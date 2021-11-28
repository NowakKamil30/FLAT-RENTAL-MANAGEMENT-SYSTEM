package pl.kamilnowak.flatrentalmanagementsystem.apartmet.mapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.kamilnowak.flatrentalmanagementsystem.apartmet.dto.*;
import pl.kamilnowak.flatrentalmanagementsystem.apartmet.entity.*;

@Configuration
public class MapperConfig {

    @Bean
    public ModelMapper getModelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.createTypeMap(Document.class, DocumentDHO.class)
                .addMappings(new PropertyMap<Document, DocumentDHO>() {
            @Override
            protected void configure() {
                map().setId(source.getId());
                map().setDocument(source.getDocument());
                map().setName(source.getName());
                map().setTenantId(source.getTenant().getId());
            }
        });
        modelMapper.createTypeMap(Currency.class, CurrencyDHO.class)
                .addMappings(new PropertyMap<Currency, CurrencyDHO>() {
                    @Override
                    protected void configure() {
                        map().setId(source.getId());
                        map().setName(source.getName());
                    }
                });
        modelMapper.createTypeMap(Image.class, ImageDHO.class)
                .addMappings(new PropertyMap<Image, ImageDHO>() {
                    @Override
                    protected void configure() {
                        map().setPhoto(source.getPhoto());
                        map().setTitle(source.getTitle());
                        map().setUploadDate(source.getUploadDate());
                        map().setId(source.getId());
                        map().setApartmentId(source.getApartment().getId());
                    }
                });
        modelMapper.createTypeMap(Apartment.class, ApartmentDHO.class)
                .addMappings(new PropertyMap<Apartment, ApartmentDHO>() {
                    @Override
                    protected void configure() {
                        map().setId(source.getId());
                        map().setName(source.getName());
                        map().setDescription(source.getDescription());
                        map().setLatitude(source.getLatitude());
                        map().setLongitude(source.getLongitude());
                        map().setCity(source.getCity());
                        map().setCountry(source.getCountry());
                        map().setStreet(source.getStreet());
                        map().setPostcode(source.getPostcode());
                        map().setHouseNumber(source.getHouseNumber());
                    }
                });
        modelMapper.createTypeMap(Tenant.class, TenantDHO.class)
                .addMappings(new PropertyMap<Tenant, TenantDHO>() {
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
                        map().setCurrencyId(source.getCurrency().getId());
                    }
                });
        return modelMapper;
    }
}
