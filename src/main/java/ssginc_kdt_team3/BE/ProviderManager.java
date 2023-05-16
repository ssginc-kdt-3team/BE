//package ssginc_kdt_team3.BE;
//
//import lombok.Getter;
//import org.springframework.security.authentication.AuthenticationEventPublisher;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.AuthenticationProvider;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.web.bind.annotation.GetMapping;
//
//import java.util.Iterator;
//import java.util.List;
//
//@Getter
//public class ProviderManager implements AuthenticationManager {
//
//    private AuthenticationEventPublisher eventPublisher;
//    private List<AuthenticationProvider> providers;
//    private AuthenticationManager parent;
//    private boolean eraseCredentialsAfterAuthentication;
//
//    @Override
//    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
//        Class<? extends Authentication> toTest = authentication.getClass();
//        AuthenticationException lastException = null;
//        AuthenticationException parentException = null;
//        Authentication result = null;
//        Authentication parentResult = null;
//
//        Iterator var8 = this.getProviders().iterator();
//
//        while (var8.hasNext()) {
//            AuthenticationProvider provider = (AuthenticationProvider) var8.next();
//            if (provider.supports(toTest)) {
//                try {
//                    result = provider.authenticate(authentication);
//                    if (result != null) {
//                        this.copyDetails(authentication, result);
//                        break;
//                    }
//            }
//
//                return null;
//        }
//    }
//}
