package ssginc_kdt_team3.BE;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ssginc_kdt_team3.BE.domain.Admin;
import ssginc_kdt_team3.BE.repository.admin.JpaDateAdminRepository;

@RequiredArgsConstructor
@Service
public class AdminUserDetailService implements UserDetailsService {

    private final JpaDateAdminRepository adminRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Admin admin =  adminRepository.findByLoginId(username)
                .orElseThrow(() -> new UsernameNotFoundException("없는 사용자"));

        return new AdminDetail(admin);
    }
}
