package com.uzima.services;

import com.uzima.Mapper.ConsultationMapper;
import com.uzima.dtos.ConsultationRequest;
import com.uzima.dtos.ConsultationResponse;
import com.uzima.enums.VisitStatus;
import com.uzima.models.Consultation;
import com.uzima.models.Doctor;
import com.uzima.models.Visit;
import com.uzima.repository.ConsultationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ConsultationService {

    private final ConsultationRepository consultationRepository;
    private final VisitService visitService;

    @Transactional
    public ConsultationResponse createConsultation(ConsultationRequest request) {
        Visit visit = visitService.getActiveVisit(request.getVisitId());
        if (visit.getVisitStatus() == VisitStatus.CANCELLED) {
            throw new IllegalArgumentException("Cannot create a consultation for a cancelled visit.");
        }
        if (consultationRepository.existsByVisitIdAndDeletedFlagFalse(visit.getId())) {
            throw new IllegalArgumentException("Visit with id: " + visit.getId()
                    + " already has an active consultation.");
        }

        Doctor doctor = visit.getDoctor();
        validateRequestedDoctor(request.getDoctorId(), doctor);

        Consultation consultation = ConsultationMapper.fromRequest(request);
        consultation.setVisit(visit);
        consultation.setDoctor(doctor);
        consultation.setConsultationDateTime(request.getConsultationDateTime() == null
                ? LocalDateTime.now()
                : request.getConsultationDateTime());
        consultation.setDeletedFlag(false);

        if (visit.getVisitStatus() == VisitStatus.CHECKED_IN || visit.getVisitStatus() == VisitStatus.TRIAGED) {
            visit.setVisitStatus(VisitStatus.IN_CONSULTATION);
        }

        return ConsultationMapper.toResponse(consultationRepository.save(consultation));
    }

    @Transactional(readOnly = true)
    public ConsultationResponse getConsultation(Long id) {
        return ConsultationMapper.toResponse(getActiveConsultation(id));
    }

    @Transactional(readOnly = true)
    public List<ConsultationResponse> getAllConsultations() {
        return consultationRepository.findAllByDeletedFlagFalse().stream()
                .map(ConsultationMapper::toResponse)
                .toList();
    }

    @Transactional(readOnly = true)
    public ConsultationResponse getConsultationByVisit(Long visitId) {
        return ConsultationMapper.toResponse(consultationRepository.findByVisitIdAndDeletedFlagFalse(visitId)
                .orElseThrow(() -> new RuntimeException(
                        "Consultation for visit with id: " + visitId + " could not be found")));
    }

    @Transactional(readOnly = true)
    public List<ConsultationResponse> getConsultationsByDoctor(Long doctorId) {
        return consultationRepository.findByDoctorIdAndDeletedFlagFalse(doctorId).stream()
                .map(ConsultationMapper::toResponse)
                .toList();
    }

    @Transactional
    public ConsultationResponse updateConsultation(Long id, ConsultationRequest request) {
        Consultation consultation = getActiveConsultation(id);
        Visit visit = visitService.getActiveVisit(request.getVisitId());
        LocalDateTime existingConsultationDateTime = consultation.getConsultationDateTime();
        if (!visit.getId().equals(consultation.getVisit().getId())) {
            throw new IllegalArgumentException("A consultation cannot be moved to a different visit.");
        }
        if (visit.getVisitStatus() == VisitStatus.CANCELLED) {
            throw new IllegalArgumentException("Cannot modify a consultation for a cancelled visit.");
        }

        validateRequestedDoctor(request.getDoctorId(), consultation.getDoctor());
        ConsultationMapper.updateConsultation(consultation, request);
        if (request.getConsultationDateTime() == null) {
            consultation.setConsultationDateTime(existingConsultationDateTime);
        }

        return ConsultationMapper.toResponse(consultationRepository.save(consultation));
    }

    @Transactional
    public ConsultationResponse deleteConsultation(Long id) {
        Consultation consultation = getActiveConsultation(id);
        consultation.setDeletedFlag(true);
        return ConsultationMapper.toResponse(consultationRepository.save(consultation));
    }

    private Consultation getActiveConsultation(Long id) {
        return consultationRepository.findByIdAndDeletedFlagFalse(id)
                .orElseThrow(() -> new RuntimeException("Consultation with id: " + id + " could not be found"));
    }

    private void validateRequestedDoctor(Long requestedDoctorId, Doctor actualDoctor) {
        if (requestedDoctorId != null && !requestedDoctorId.equals(actualDoctor.getId())) {
            throw new IllegalArgumentException("Consultation doctor must match the visit doctor.");
        }
    }
}
