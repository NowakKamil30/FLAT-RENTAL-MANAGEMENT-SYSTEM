package pl.kamilnowak.flatrentalmanagementsystem.security.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import pl.kamilnowak.flatrentalmanagementsystem.security.entity.VerificationToken;
import pl.kamilnowak.flatrentalmanagementsystem.security.repository.VerificationTokenRepository;
import pl.kamilnowak.flatrentalmanagementsystem.util.service.CRUDOperation;
import pl.kamilnowak.flatrentalmanagementsystem.util.service.PageableHelper;

import java.time.LocalDateTime;

@Service
@Log4j2
public class VerificationTokenService implements CRUDOperation<VerificationToken, Long> {

    private final VerificationTokenRepository verificationTokenRepository;
    private final PageableHelper pageableHelper;

    public VerificationTokenService(VerificationTokenRepository verificationTokenRepository, PageableHelper pageableHelper) {
        this.verificationTokenRepository = verificationTokenRepository;
        this.pageableHelper = pageableHelper;
    }

    public VerificationToken getVerificationToken(String token) {
        log.debug("read verification token token: " + token);
        return verificationTokenRepository.findVerificationTokenByToken(token);
    }

    @Override
    public VerificationToken createObject(VerificationToken verificationToken) {
        log.debug("create verification token");
        verificationToken.setCreateTime(LocalDateTime.now());
        return verificationTokenRepository.save(verificationToken);
    }

    @Override
    public void deleteById(Long aLong) {
        log.debug("delete verification token id: " + aLong);
        verificationTokenRepository.deleteById(aLong);
    }

    @Override
    public VerificationToken getObjectById(Long aLong) {
        log.debug("read verification token id: " + aLong);
        return verificationTokenRepository.getById(aLong);
    }

    @Override
    public Page<VerificationToken> getAllObject(int page) {
        log.debug("read verification token page: " + page);
        return verificationTokenRepository.findAll(pageableHelper.countPageable(page));
    }

    @Override
    public VerificationToken updateObject(VerificationToken verificationToken, Long aLong) {
        log.debug("update verification token id: " + aLong);
        if (verificationTokenRepository.findById(aLong).isEmpty()) {
            return verificationTokenRepository.save(verificationToken);
        }
        verificationToken.setId(aLong);
        return verificationTokenRepository.save(verificationToken);
    }

    public void deleteAllByCreateTimeIsBefore(LocalDateTime localDateTime) {
        log.debug("delete all Token not valid");
        verificationTokenRepository.deleteAllByCreateTimeIsBefore(localDateTime);
    }
}
