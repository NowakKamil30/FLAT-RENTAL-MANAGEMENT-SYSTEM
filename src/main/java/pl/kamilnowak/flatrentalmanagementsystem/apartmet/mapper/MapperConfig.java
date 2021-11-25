package pl.kamilnowak.flatrentalmanagementsystem.apartmet.mapper;

import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import pl.kamilnowak.flatrentalmanagementsystem.apartmet.dto.*;
import pl.kamilnowak.flatrentalmanagementsystem.apartmet.entity.*;

import java.util.stream.Collectors;

@Configurable
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
                        map().setImageIds(source.getImages()
                                .stream()
                                .map(Image::getId)
                                .collect(Collectors.toList()));
                        map().setTenantIds(source.getTenants()
                                .stream()
                                .map(Tenant::getId)
                                .collect(Collectors.toList()));
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
                        map().setDocuemntIds(source.getDocuments()
                                .stream()
                                .map(Document::getId)
                                .collect(Collectors.toList()));
                        map().setCurrencyId(source.getCurrency().getId());
                    }
                });
        return modelMapper;
    }
}
