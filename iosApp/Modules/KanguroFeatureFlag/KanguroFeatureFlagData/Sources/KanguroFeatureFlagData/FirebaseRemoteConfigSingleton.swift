import FirebaseRemoteConfig
import Firebase

public class FirebaseRemoteConfigSingleton {

    public static let shared: RemoteConfig = {
        let config = RemoteConfig.remoteConfig()
        let settings = RemoteConfigSettings()
#if DEBUG
        settings.minimumFetchInterval = 0
#endif
        config.configSettings = settings
        config.addOnConfigUpdateListener { configUpdate, error in
            guard error == nil, configUpdate != nil else {
                return
            }
            config.activate()
        }
        return config
    }()

    private init() { }
}
