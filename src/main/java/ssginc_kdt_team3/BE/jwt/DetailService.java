package ssginc_kdt_team3.BE.jwt;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ssginc_kdt_team3.BE.domain.Admin;
import ssginc_kdt_team3.BE.domain.Owner;
import ssginc_kdt_team3.BE.domain.User;
import ssginc_kdt_team3.BE.repository.JpaUserRepository;
import ssginc_kdt_team3.BE.repository.admin.JpaDateAdminRepository;
import ssginc_kdt_team3.BE.repository.customer.JpaCustomerRepository;
import ssginc_kdt_team3.BE.repository.owner.JpaDataOwnerRepository;

@RequiredArgsConstructor
@Service
public class DetailService implements UserDetailsService {

    private final JpaDateAdminRepository adminRepository;
    private final JpaUserRepository userRepository;

    @Override
    public AdminDetail loadUserByUsername(String username) throws UsernameNotFoundException {
        Admin admin =  adminRepository.findByLoginId(username)
                .orElseThrow(() -> new UsernameNotFoundException("없는 사용자"));

        return new AdminDetail(admin);
    }

    public UserDetail loadCustomerByEmail(String Email) throws UsernameNotFoundException {
        User user =  userRepository.findByEmail(Email)
                .orElseThrow(() -> new UsernameNotFoundException("없는 사용자"));

        return new UserDetail(user);
    }
}
