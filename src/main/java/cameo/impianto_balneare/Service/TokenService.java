package cameo.impianto_balneare.Service;

import cameo.impianto_balneare.Entity.Role;
import cameo.impianto_balneare.Entity.Token;
import cameo.impianto_balneare.Entity.User;
import cameo.impianto_balneare.Repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TokenService {
    private TokenRepository tokenRepository;

    @Autowired
    public TokenService(TokenRepository tokenRepository) {
        this.tokenRepository = tokenRepository;
    }

    public boolean checkToken(String uuid, Role role) {
        var token = tokenRepository.findById(UUID.fromString(uuid));
        return token.map(value -> value.getRole().equals(role)).orElse(false);
    }

    public User getUserFromUUID(String uuid) {
        var token = tokenRepository.findById(UUID.fromString(uuid));
        return token.map(Token::getUser).orElse(null);
    }
}
