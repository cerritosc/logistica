package co.com.ingeneo.logistica.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import org.apache.commons.lang3.StringUtils;
import co.com.ingeneo.logistica.domain.PlanEntregaDet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import co.com.ingeneo.logistica.repository.PlanEntregaDetRepository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import co.com.ingeneo.logistica.common.Constants;
import co.com.ingeneo.logistica.common.ServiceResponse;
import co.com.ingeneo.logistica.commons.exception.EntidadNoEncontradaException;

import java.util.List;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@Transactional
public class PlanEntregaDetServiceImpl implements PlanEntregaDetService {

    @Autowired
    private PlanEntregaDetRepository planEntregaDetRepository;

    @Override
    @Transactional(readOnly = true, propagation = Propagation.REQUIRED)
    public Optional<PlanEntregaDet> get(Integer skPlanEntregaDet) {
            return planEntregaDetRepository.findById(skPlanEntregaDet);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<PlanEntregaDet> findBySkPlanEntregaDet(Integer skPlanEntregaDet) {
            return planEntregaDetRepository.findBySkPlanEntregaDet(skPlanEntregaDet);
    }

    @Override
    public PlanEntregaDet create(PlanEntregaDet planEntregaDet) {
            return planEntregaDetRepository.save(planEntregaDet);
    }

    @Override
    public PlanEntregaDet update(PlanEntregaDet planEntregaDet) {
            return planEntregaDetRepository.save(planEntregaDet);
    }

    @Override
    public ServiceResponse delete(Integer skPlanEntregaDet) {
            ServiceResponse serviceResponse = new ServiceResponse(false, Constants.MSG_EXCEPCION_ACCION);
            PlanEntregaDet planEntregaDet = get(skPlanEntregaDet)
                    .orElseThrow(() -> new EntidadNoEncontradaException(skPlanEntregaDet.toString()));
            planEntregaDetRepository.delete(planEntregaDet);

            serviceResponse.setMessage(Constants.MSG_ELIMINADO_EXITOSO);
            serviceResponse.setSuccess(true);
            serviceResponse.setData(null);
            return serviceResponse;
    }

    @Override
    public List<PlanEntregaDet> get() {
        return planEntregaDetRepository.findAll();
    }

	// metodos para obtener data como lista
    @Override
    @Transactional(readOnly = true)
    public Slice<PlanEntregaDet> getList(Integer page, Integer rows) {
            return planEntregaDetRepository.findAll(PageRequest.of(page - 1, rows));
    }
	
    @Override
    @Transactional(readOnly = true)
    public Slice<PlanEntregaDet> getListByQ(String q, Pageable page) {
        if(!StringUtils.isBlank(q)) {
            return planEntregaDetRepository.findAll(page);
        }
        else return planEntregaDetRepository.findAll(page);
    }
	
    // metodos para DataTable
}
