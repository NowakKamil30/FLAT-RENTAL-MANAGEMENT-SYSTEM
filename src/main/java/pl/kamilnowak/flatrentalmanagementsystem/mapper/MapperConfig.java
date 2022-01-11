package pl.kamilnowak.flatrentalmanagementsystem.mapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.kamilnowak.flatrentalmanagementsystem.apartment.dto.*;
import pl.kamilnowak.flatrentalmanagementsystem.apartment.entity.*;
import pl.kamilnowak.flatrentalmanagementsystem.security.dto.LoginUserDTO;
import pl.kamilnowak.flatrentalmanagementsystem.security.dto.UserDataDTO;
import pl.kamilnowak.flatrentalmanagementsystem.security.dto.VerificationTokenDTO;
import pl.kamilnowak.flatrentalmanagementsystem.security.entity.LoginUser;
import pl.kamilnowak.flatrentalmanagementsystem.security.entity.UserData;
import pl.kamilnowak.flatrentalmanagementsystem.security.entity.VerificationToken;

@Configuration
public class MapperConfig {

    @Bean
    public ModelMapper getModelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.createTypeMap(Document.class, DocumentDTO.class)
                .addMappings(new PropertyMap<Document, DocumentDTO>() {
            @Override
            protected void configure() {
                map().setId(source.getId());
                map().setDocument(source.getDocument());
                map().setName(source.getName());
                map().setTenantId(source.getTenant().getId());
            }
        });
        modelMapper.createTypeMap(Currency.class, CurrencyDTO.class)
                .addMappings(new PropertyMap<Currency, CurrencyDTO>() {
                    @Override
                    protected void configure() {
                        map().setId(source.getId());
                        map().setName(source.getName());
                    }
                });
        modelMapper.createTypeMap(Image.class, ImageDTO.class)
                .addMappings(new PropertyMap<Image, ImageDTO>() {
                    @Override
                    protected void configure() {
                        map().setPhoto(source.getPhoto());
                        map().setTitle(source.getTitle());
                        map().setUploadDate(source.getUploadDate());
                        map().setId(source.getId());
                        map().setApartmentId(source.getApartment().getId());
                    }
                });
        modelMapper.createTypeMap(Apartment.class, ApartmentDTO.class)
                .addMappings(new PropertyMap<Apartment, ApartmentDTO>() {
                    @Override
                    protected void configure() {
                        map().setId(source.getId());
                        map().setName(source.getName());
                        map().setDescription(source.getDescription());
                        map().setCity(source.getCity());
                        map().setCountry(source.getCountry());
                        map().setStreet(source.getStreet());
                        map().setPostcode(source.getPostcode());
                        map().setHouseNumber(source.getHouseNumber());
                        map().setUserId(source.getUserData().getId());
                    }
                });
        modelMapper.createTypeMap(Tenant.class, TenantDTO.class)
                .addMappings(new PropertyMap<Tenant, TenantDTO>() {
                    @Override
                    protected void configure() {
                        map().setId(source.getId());
                        map().setFirstName(source.getFirstName());
                        map().setLastName(source.getLastName());
                        map().setPhoneNumber(source.getPhoneNumber());
                        map().setMail(source.getMail());
                        map().setFee(source.getFee());
                        map().setActive(source.isActive());
                        map().setPaid(source.isPaid());
                        map().setDescription(source.getDescription());
                        map().setEndDate(source.getEndDate());
                        map().setStartDate(source.getStartDate());
                        map().setPaidDate(source.getPaidDate());
                        map().setApartmentId(source.getApartment().getId());
                        map().setCurrency(source.getCurrency());
                        map().setExtraCosts(source.getExtraCosts());
                        map().setDayToPay(source.getDayToPay());
                    }
                });
        modelMapper.createTypeMap(LoginUser.class, LoginUserDTO.class)
                .addMappings(new PropertyMap<LoginUser, LoginUserDTO>() {
                    @Override
                    protected void configure() {
                        map().setId(source.getId());
                        map().setMail(source.getMail());
                        map().setEnable(source.isEnable());
                        map().setRole(source.getRole());
                    }
                });
        modelMapper.createTypeMap(UserData.class, UserDataDTO.class)
                .addMappings(new PropertyMap<UserData, UserDataDTO>() {
                    @Override
                    protected void configure() {
                        map().setId(source.getId());
                        map().setActiveAccountData(source.getActiveAccountData());
                        map().setLastName(source.getLastName());
                        map().setFirstName(source.getFirstName());
                        map().setCreateUserData(source.getCreateUserData());
                        map().setLoginUserId(source.getLoginUser().getId());
                    }
                });
        modelMapper.createTypeMap(VerificationToken.class, VerificationTokenDTO.class)
                .addMappings(new PropertyMap<VerificationToken, VerificationTokenDTO>() {
                    @Override
                    protected void configure() {
                        map().setId(source.getId());
                        map().setToken(source.getToken());
                        map().setLoginUserId(source.getLoginUser().getId());
                        map().setCreateTime(source.getCreateTime());
                    }
                });
        modelMapper.createTypeMap(ExtraCost.class, ExtraCostDTO.class)
                .addMappings(new PropertyMap<ExtraCost, ExtraCostDTO>() {
                    @Override
                    protected void configure() {
                        map().setId(source.getId());
                        map().setName(source.getName());
                        map().setExtraCost(source.getExtraCost());
                    }
                });

        return modelMapper;
    }
}
