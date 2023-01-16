package model.services;

import java.time.Duration;

import model.entities.CarRental;
import model.entities.Invoice;

public class RentalService {

	private double pricePerHour;
	private double pricePerDay;
	
	private TaxService taxService;

	public RentalService(double pricePerHour, double pricePerDay, TaxService taxService) {
		this.pricePerHour = pricePerHour;
		this.pricePerDay = pricePerDay;
		this.taxService = taxService;
	}

	public void processInvoice(CarRental carRental) {
		
		//Encontrar a duração em fração de horas(1º achar a diferença em minutos, 2º calcular a fraçao
		double minutes = Duration.between(carRental.getStart(), carRental.getFinish()).toMinutes();
		double hours = minutes / 60.0;
		
		double basicPayment;
		if(hours <= 12.0) {
			//pegar em horas
			basicPayment = pricePerHour * Math.ceil(hours);
		}else {
			//pegar em dias
			basicPayment = pricePerDay * Math.ceil(hours/24.0);
		}
		//Calcular imposto, com base no pagamento basico
		double tax = taxService.tax(basicPayment);
		//Formula para calcular a fatura para qualquer valor
		carRental.setInvoice(new Invoice(basicPayment, tax));
	}
	
}