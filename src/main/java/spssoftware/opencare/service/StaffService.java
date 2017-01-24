package spssoftware.opencare.service;

import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import spssoftware.opencare.domain.Staff;
import spssoftware.opencare.repository.StaffRepository;

import java.util.List;
import java.util.Map;

@Service
public class StaffService {

    private StaffRepository staffRepository;
    private OpenCareObjectMapper openCareObjectMapper;

    @Autowired
    public StaffService(StaffRepository userRepository,
                        OpenCareObjectMapper openCareObjectMapper) {
        this.staffRepository = userRepository;
        this.openCareObjectMapper = openCareObjectMapper;
    }

    public List<Staff> find(List<String> fields, Map<String, List<String>> constraints) {

        return Lists.newArrayList(staffRepository.find(fields, constraints));
    }

    public Staff get(String id) {
        return staffRepository.get(id);
    }

    @Transactional
    public Staff save(Staff staff) {
        return staffRepository.save(staff);
    }

    @Transactional
    public Staff patch(String id, JSONObject patch) {

        Staff staff = get(id);

        if (staff == null) {
            return null;
        } else {
            return staffRepository.save(openCareObjectMapper.patchObject(patch, get(id)));
        }
    }

    @Transactional
    public void delete(String id) {
        staffRepository.delete(id);
    }
}