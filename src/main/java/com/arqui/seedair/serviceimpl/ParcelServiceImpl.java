package com.arqui.seedair.serviceimpl;

import com.arqui.seedair.dtos.ParcelDTO;
import com.arqui.seedair.dtos.ParcelDTOByCustomerId;
import com.arqui.seedair.dtos.ParcelResponseDTO;
import com.arqui.seedair.entities.Customer;
import com.arqui.seedair.entities.Parcel;
import com.arqui.seedair.exceptions.IncompleteDataException;
import com.arqui.seedair.exceptions.InvalidDataRangeException;
import com.arqui.seedair.exceptions.KeyRepeatedDataExeception;
import com.arqui.seedair.exceptions.ResourceNotFoundException;
import com.arqui.seedair.repositories.CustomerRepository;
import com.arqui.seedair.repositories.ParcelRepository;
import com.arqui.seedair.services.ParcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ParcelServiceImpl implements ParcelService{
    @Autowired
    ParcelRepository parcelRepository;
    @Autowired
    CustomerRepository customerRepository;
    @Override
    public Parcel add(Parcel parcel) {
        return parcelRepository.save(parcel);
    }

    @Override
    public Parcel findById(Long id) {
        return parcelRepository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("La parcela con ID " + id + " no existe."));
    }



    @Override
    public ParcelDTO register(ParcelDTO parcelDTO) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        Customer customer = customerRepository.findByUser_username(username);

        if (parcelDTO.getLocationText().isBlank()){
            throw new IncompleteDataException("El nombre de la ubicación es obligatoria");
        }
        if (parcelDTO.getTotalHectares() < 0){
            throw new InvalidDataRangeException("El total de hectáreas no puede ser negativa");
        }
        if (parcelDTO.getLatitude() == null){
            throw new IncompleteDataException("La latitud es obligatoria");
        }
        if (parcelDTO.getLongitude() == null){
            throw new IncompleteDataException("La longitud es obligatoria");
        }
        if (parcelDTO.getLatitude2() == null){
            throw new IncompleteDataException("La segunda latitud es obligatoria");
        }
        if (parcelDTO.getLongitude2() == null){
            throw new IncompleteDataException("La segunda longitud es obligatoria");
        }
        if (parcelDTO.getLatitude() < -90 || parcelDTO.getLatitude() > 90) {
            throw new InvalidDataRangeException("La latitud debe estar entre -90 y 90");
        }
        if (parcelDTO.getLongitude() < -180 || parcelDTO.getLongitude() > 180) {
            throw new InvalidDataRangeException("La longitud debe estar entre -180 y 180");
        }
        if (parcelDTO.getLatitude2() < -90 || parcelDTO.getLatitude2() > 90) {
            throw new InvalidDataRangeException("La segunda latitud debe estar entre -90 y 90");
        }
        if (parcelDTO.getLongitude2() < -180 || parcelDTO.getLongitude2() > 180) {
            throw new InvalidDataRangeException("La segunda longitud debe estar entre -180 y 180");
        }

        Boolean exists = parcelRepository.existsByLatitudeAndLongitude(parcelDTO.getLatitude(), parcelDTO.getLongitude());
        if (exists){
            throw new KeyRepeatedDataExeception("La parcela ya está registrada");
        }
        Parcel newParcel = new Parcel(null,
                parcelDTO.getLocationText(), parcelDTO.getTotalHectares(),
                parcelDTO.getLatitude(), parcelDTO.getLongitude(),
                parcelDTO.getLatitude2(), parcelDTO.getLongitude2(), LocalDate.now(),
                null, customer, true
        );
        parcelRepository.save(newParcel);
        return parcelDTO;
    }

    @Override
    public ParcelDTOByCustomerId findParcelById(Long id) {
        Parcel parcel = findById(id);
        if(parcel != null){
            ParcelDTOByCustomerId parcelDTOByCustomerId = new ParcelDTOByCustomerId(parcel.getId(), parcel.getLocationText(),parcel.getTotalHectares(), parcel.getLatitude(),
                    parcel.getLongitude(), parcel.getLatitude2(), parcel.getLongitude2(), parcel.getCreatedAt(), parcel.getIsActive());
            return parcelDTOByCustomerId;
        }
        return null;
    }

    @Override
    public ParcelDTOByCustomerId update(ParcelDTOByCustomerId parcelDTO) {

        Parcel parcel = parcelRepository.findById(parcelDTO.getId())
                .orElseThrow(() -> new ResourceNotFoundException("La parcela con ID " + parcelDTO.getId() + " no existe."));

        if (parcelDTO.getLocationText().isBlank()){
            throw new IncompleteDataException("El nombre de la ubicación es obligatoria");
        }
        if (parcelDTO.getTotalHectares() < 0){
            throw new InvalidDataRangeException("El total de hectáreas no puede ser negativa");
        }
        if (parcelDTO.getLatitude() == null){
            throw new IncompleteDataException("La latitud es obligatoria");
        }
        if (parcelDTO.getLongitude() == null){
            throw new IncompleteDataException("La longitud es obligatoria");
        }
        if (parcelDTO.getLatitude2() == null){
            throw new IncompleteDataException("La segunda latitud es obligatoria");
        }
        if (parcelDTO.getLongitude2() == null){
            throw new IncompleteDataException("La segunda longitud es obligatoria");
        }
        if (parcelDTO.getLatitude() < -90 || parcelDTO.getLatitude() > 90) {
            throw new InvalidDataRangeException("La latitud debe estar entre -90 y 90");
        }
        if (parcelDTO.getLongitude() < -180 || parcelDTO.getLongitude() > 180) {
            throw new InvalidDataRangeException("La longitud debe estar entre -180 y 180");
        }
        if (parcelDTO.getLatitude2() < -90 || parcelDTO.getLatitude2() > 90) {
            throw new InvalidDataRangeException("La segunda latitud debe estar entre -90 y 90");
        }
        if (parcelDTO.getLongitude2() < -180 || parcelDTO.getLongitude2() > 180) {
            throw new InvalidDataRangeException("La segunda longitud debe estar entre -180 y 180");
        }

        parcel.setLocationText(parcelDTO.getLocationText());
        parcel.setTotalHectares(parcelDTO.getTotalHectares());
        parcel.setLatitude(parcelDTO.getLatitude());
        parcel.setLongitude(parcelDTO.getLongitude());
        parcel.setLatitude2(parcelDTO.getLatitude2());
        parcel.setLongitude2(parcelDTO.getLongitude2());
        parcel.setIsActive(parcelDTO.getIsActive());

        parcelRepository.save(parcel);

        return parcelDTO;
    }

    /////////////////
    @Override
    public List<ParcelDTOByCustomerId> listParcelDTOById(Long customerId) {


        //Para mostrar solo las que se encuentran activas (que no fueron eliminadas logicamente hablando)
        List<Parcel> customerParcelsList = parcelRepository.findByCustomerId(customerId);

        List<ParcelDTOByCustomerId> parcelDTOList = customerParcelsList.stream()
                .filter(p -> p.getIsActive() && p.getIsActive() != null)//pra que jale solo a los activos
                .map(
                        p->new ParcelDTOByCustomerId(
                                p.getId(),
                                p.getLocationText(),
                                p.getTotalHectares(),
                                p.getLatitude(),
                                p.getLongitude(),
                                p.getLatitude2(),
                                p.getLongitude2(),
                                p.getCreatedAt(),
                                p.getIsActive()


                        )).toList();
        return parcelDTOList;

    }
    @Override
    public List<ParcelDTOByCustomerId> listParcelDTOByCustomer() {

        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        Customer customer = customerRepository.findByUser_username(username);



        //Para mostrar solo las que se encuentran activas (que no fueron eliminadas logicamente hablando)
        List<Parcel> customerParcelsList = parcelRepository.findByCustomerId(customer.getId());

        List<ParcelDTOByCustomerId> parcelDTOList = customerParcelsList.stream()
                .filter(p -> p.getIsActive() && p.getIsActive() != null)//pra que jale solo a los activos
                .map(
                        p->new ParcelDTOByCustomerId(
                                p.getId(),
                                p.getLocationText(),
                                p.getTotalHectares(),
                                p.getLatitude(),
                                p.getLongitude(),
                                p.getLatitude2(),
                                p.getLongitude2(),
                                p.getCreatedAt(),
                                p.getIsActive()


                        )).toList();
        return parcelDTOList;

    }

    @Override
    public List<ParcelResponseDTO> listParcels() {
        List<Parcel> parcels = parcelRepository.findAll();

        List<ParcelResponseDTO> parcelResponseDTOList = parcels.stream()
                .map(p -> {
                    Customer customer = p.getCustomer();
                    String customerName = (customer != null)
                            ? customer.getFirstName() + " " + customer.getLastName() : null;
                    return new ParcelResponseDTO(
                            p.getId(),
                            p.getLocationText(),
                            p.getTotalHectares(),
                            p.getLatitude(),
                            p.getLongitude(),
                            p.getLatitude2(),
                            p.getLongitude2(),
                            p.getCreatedAt(),
                            customerName,
                            p.getIsActive()
                    );
                }).toList();

        return parcelResponseDTOList;
    }

}
