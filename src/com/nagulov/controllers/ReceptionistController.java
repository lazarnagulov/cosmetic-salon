package com.nagulov.controllers;

import java.time.LocalDateTime;
import java.util.HashMap;

import com.nagulov.data.DataBase;
import com.nagulov.treatments.CosmeticService;
import com.nagulov.treatments.Treatment;
import com.nagulov.treatments.TreatmentBuilder;
import com.nagulov.treatments.TreatmentStatus;
import com.nagulov.users.Beautician;
import com.nagulov.users.Client;

public class ReceptionistController implements ReceptionistControl {

	private static ReceptionistController instance = null;
	
	protected ReceptionistController() {
		
	}
	
	public static ReceptionistController getInstance() {
		if(instance == null) {
			instance = new ReceptionistController();
		}
		return instance;
	}
	
	@Override
	public Treatment createTreatment(TreatmentStatus status, CosmeticService service, String treatment,
			Beautician beautician, LocalDateTime date, Client client) {
		return new TreatmentBuilder()
				.setBeautician(beautician)
				.setClient(client)
				.setDate(date)
				.setService(service)
				.setStatus(status)
				.setTreatment(treatment)
				.build();
	}

	@Override
	public void removeTreatment(Treatment treatment) {
		DataBase.treatments.remove(treatment.getId());
	}

	@Override
	public void removeTreatment(int id) {
		DataBase.treatments.remove(id);
	}

	@Override
	public void updateTreatment(Treatment treatment, HashMap<String, String> updateMap) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Treatment getTreatment(int id) {
		return DataBase.treatments.get(id);
	}

}
