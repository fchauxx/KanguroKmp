package com.insurtech.kanguro.ui.scenes.chatbot

import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.insurtech.kanguro.R
import com.insurtech.kanguro.analytics.AnalyticsEnums
import com.insurtech.kanguro.core.utils.BitmapUtils
import com.insurtech.kanguro.core.utils.IntentUtils
import com.insurtech.kanguro.databinding.FragmentChatBotMapBinding
import com.insurtech.kanguro.domain.model.VetPlace
import com.insurtech.kanguro.ui.custom.KanguroBottomSheetFragment
import com.insurtech.kanguro.ui.scenes.chatbot.adapter.VetPlacesAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ChatBotMapFragment :
    KanguroBottomSheetFragment<FragmentChatBotMapBinding>(),
    OnMapReadyCallback {

    override val screenName: AnalyticsEnums.Screen =
        AnalyticsEnums.Screen.RecommendedLocationsChatBot

    override val viewModel: ChatBotMapViewModel by viewModels()

    private lateinit var googleMap: GoogleMap
    private lateinit var currentLocation: Location
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient

    private lateinit var placesAdapter: VetPlacesAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = super.onCreateView(inflater, container, savedInstanceState)

        fusedLocationProviderClient =
            LocationServices.getFusedLocationProviderClient(requireActivity())

        val mapFragment =
            childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setCloseButton()

        viewModel.isLoading.observe(viewLifecycleOwner) { isLoading ->
            if (isLoading) {
                binding.isLoading.visibility = View.VISIBLE
            } else {
                binding.isLoading.visibility = View.GONE
            }
        }
    }

    override fun onCreateBinding(inflater: LayoutInflater) =
        FragmentChatBotMapBinding.inflate(inflater)

    override fun onMapReady(p0: GoogleMap) {
        googleMap = p0
        getCurrentLocation()
    }

    private fun setCloseButton() {
        binding.closeButton.setOnClickListener {
            findNavController().navigateUp()
        }
    }

    private fun onPhonePressed(vetPlace: VetPlace) {
        vetPlace.phone?.let {
            IntentUtils.openDialIntent(requireContext(), it)
        }
    }

    private fun setupVetPlaces() {
        val bottomSheetBehavior = BottomSheetBehavior.from(binding.placesBottomSheet)
        bottomSheetBehavior.isHideable = false

        placesAdapter = VetPlacesAdapter(::onPhonePressed)

        binding.recyclerView.adapter = placesAdapter
        binding.recyclerView.layoutManager = LinearLayoutManager(requireContext())

        viewModel.vetPlacesResumed.observe(viewLifecycleOwner) { places ->
            if (places.isNotEmpty()) {
                setupMarkers(places)
            }
        }

        viewModel.vetPlacesDetailed.observe(viewLifecycleOwner) { places ->
            if (places.isEmpty()) {
                binding.placesBottomSheet.visibility = View.GONE
            } else {
                binding.placesBottomSheet.visibility = View.VISIBLE
                placesAdapter.submitList(places)
                setupMarkers(places)
            }
        }
    }

    private fun getCurrentLocation() {
        if (isPermissionGranted()) {
            getLocation()
        } else {
            requestPermission.launch(
                arrayOf(
                    android.Manifest.permission.ACCESS_FINE_LOCATION,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION
                )
            )
        }
    }

    private fun isPermissionGranted(): Boolean {
        val isFineLocationGranted = ActivityCompat.checkSelfPermission(
            requireContext(),
            android.Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        val isCoarseLocationGranted = ActivityCompat.checkSelfPermission(
            requireContext(),
            android.Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED

        return isFineLocationGranted && isCoarseLocationGranted
    }

    private val requestPermission =
        registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
            if (isPermissionGranted()) {
                getLocation()
            }
        }

    private fun getLocation() {
        googleMap.isMyLocationEnabled = true

        fusedLocationProviderClient.lastLocation.addOnSuccessListener {
            if (it != null) {
                currentLocation = it
                val currentLatLng = LatLng(currentLocation.latitude, currentLocation.longitude)
                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 13f))
                setupVetPlaces()
                viewModel.loadPlaces(currentLocation)
            }
        }
    }

    private fun setupMarkers(places: List<VetPlace>) {
        googleMap.clear()
        val mapBounds = LatLngBounds.builder()

        if (this::currentLocation.isInitialized) {
            val currentLocation = LatLng(currentLocation.latitude, currentLocation.longitude)
            mapBounds.include(currentLocation)
        }

        val markerIcon =
            BitmapUtils.drawableToBitmapDescriptor(
                requireContext(),
                R.drawable.ic_nearby_vet_pin
            )

        for (place in places) {
            val placePosition = LatLng(place.lat ?: 0.0, place.lng ?: 0.0)
            val markerOptions = MarkerOptions()
                .title(place.name)
                .snippet(place.address)
                .position(placePosition)
                .icon(markerIcon)

            googleMap.addMarker(markerOptions)
            mapBounds.include(placePosition)
        }

        googleMap.setOnMapLoadedCallback {
            googleMap.animateCamera(CameraUpdateFactory.newLatLngBounds(mapBounds.build(), 100))
        }
    }
}
