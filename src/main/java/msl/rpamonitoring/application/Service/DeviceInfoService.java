package msl.rpamonitoring.application.Service;

import java.time.LocalDateTime;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import msl.rpamonitoring.application.Entity.DeviceInfo;
import msl.rpamonitoring.application.Entity.Users;
import msl.rpamonitoring.application.Repository.DeviceInfoRepository;

@Service
@Slf4j
public class DeviceInfoService {
    @Autowired
    private DeviceInfoRepository deviceInfoRepository;

    public void saveDeviceInfo(Users user,String deviceToken) {
        try{
        DeviceInfo deviceInfo = deviceInfoRepository.findByUserId(user.getId())
        .stream()
        .filter(d -> d.getDeviceToken().equals(deviceToken))
        .findFirst()
        .orElse(new DeviceInfo());


        deviceInfo.setUser(user);
        deviceInfo.setDeviceToken(deviceToken);
        deviceInfo.setLastUpdated(LocalDateTime.now());

        deviceInfoRepository.save(deviceInfo);
        }catch (Exception e){
            log.info("{}",e.getMessage());
        }

    }
}
