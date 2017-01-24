package spssoftware.opencare.service;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spssoftware.opencare.domain.Organisation;
import spssoftware.opencare.repository.OrganisationRepository;

import java.util.List;
import java.util.Map;

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

    public List<Organisation> find(List<String> fields, Map<String, List<String>> constraints) {

        return Lists.newArrayList(organisationRepository.find(fields, constraints));
    }

    public Organisation get(String id) {
        return organisationRepository.get(id);
    }

    @Transactional
    public Organisation save(Organisation organisation) {
        return organisationRepository.save(organisation);
    }

    @Transactional
    public Organisation patch(String id, JSONObject patch) {

        Organisation organisation = get(id);

        if (organisation == null) {
            return null;
        } else {
            return organisationRepository.save(openCareObjectMapper.patchObject(patch, get(id)));
        }
    }

    @Transactional
    public void delete(String id) {
        organisationRepository.delete(id);
    }
}