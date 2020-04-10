package br.com.abce.advocacia.util;

import javax.faces.event.PhaseEvent;
import javax.faces.event.PhaseId;
import javax.faces.event.PhaseListener;

public class LifeCycleListener implements PhaseListener {

    @Override
    public void afterPhase(PhaseEvent phaseEvent) {
        System.out.println("FINALIZANDO FASE: " + phaseEvent.getPhaseId());
    }

    @Override
    public void beforePhase(PhaseEvent phaseEvent) {
        System.out.println("INICIANDO FASE: " + phaseEvent.getPhaseId());
    }

    @Override
    public PhaseId getPhaseId() {
        return PhaseId.ANY_PHASE;
    }
}
