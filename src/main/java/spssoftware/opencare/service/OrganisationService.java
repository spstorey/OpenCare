package spssoftware.opencare.service;

import com.alibaba.fastjson.JSONObject;
import com.sun.org.apache.xpath.internal.operations.Or;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import spssoftware.opencare.domain.Organisation;
import spssoftware.opencare.domain.User;
import spssoftware.opencare.repository.OrganisationRepository;
import spssoftware.opencare.repository.UserRepository;

import java.util.List;

@Service
public class OrganisationService {

    private OrganisationRepository organisationRepository;
    private OpenCareObjectMapper openCareObjectMapper;

    @Autowired
    public OrganisationService(OrganisationRepository organisationRepository,
                               OpenCareObjectMapper openCareObjectMapper) {
        this.organisationRepository = organisationRepository;
        this.openCareObjectMapper = openCareObjectMapper;
    }

    public List<Organisation> list(String name, String town, String county,
                                               String country, String postCode) {
        return organisationRepository.list(name, town, county, country, postCode);
    }

    public Organisation get(String id) {
        return organisationRepository.get(id);
    }

    public String add(Organisation organisation) {
        return organisationRepository.add(organisation);
    }

    public void update(Organisation organisation) {
        organisationRepository.update(organisation);
    }

    public void patch(String id, JSONObject patch) {

        Organisation organisation = get(id);

        if (organisation != null) {
            organisationRepository.update(openCareObjectMapper.patchObject(patch, get(id)));
        }
    }

    public void delete(String id) {
        organisationRepository.delete(id);
    }
}