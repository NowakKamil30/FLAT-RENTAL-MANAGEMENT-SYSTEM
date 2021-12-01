package pl.kamilnowak.flatrentalmanagementsystem.security.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import pl.kamilnowak.flatrentalmanagementsystem.security.entity.UserData;
import pl.kamilnowak.flatrentalmanagementsystem.security.repository.UserDataRepository;
import pl.kamilnowak.flatrentalmanagementsystem.service.CRUDOperation;
import pl.kamilnowak.flatrentalmanagementsystem.service.PageableHelper;

@Service
@Log4j2
public class UserDataService implements CRUDOperation<UserData, Long> {

    private final UserDataRepository userDataRepository;
    private final PageableHelper pageableHelper;

    @Autowired
    public UserDataService(UserDataRepository userDataRepository, PageableHelper pageableHelper) {
        this.userDataRepository = userDataRepository;
        this.pageableHelper = pageableHelper;
    }

    @Override
    public UserData createObject(UserData userData) {
        log.debug("create user data");
        return userDataRepository.save(userData);
    }

    @Override
    public void deleteById(Long aLong) {
        log.debug("delete user data id: " + aLong);
        userDataRepository.deleteById(aLong);
    }

    @Override
    public UserData getObjectById(Long aLong) {
        log.debug("read user data id: " + aLong);
        return userDataRepository.getById(aLong);
    }

    @Override
    public Page<UserData> getAllObject(int page) {
        log.debug("read user datas page: " + page);
        return userDataRepository.findAll(pageableHelper.countPageable(page));
    }

    @Override
    public UserData updateObject(UserData userData, Long aLong) {
        log.debug("update user data id: " + aLong);
        if(userDataRepository.findById(aLong).isEmpty()) {
            return userDataRepository.save(userData);
        }
        userData.setId(aLong);
        return userDataRepository.save(userData);
    }
}
