package application;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Scanner;

import model.entities.CarRental;
import model.entities.Vehicle;
import model.services.BrazilTaxService;
import model.services.RentalService;

public class Program {

	public static void main(String[] args) {
		Locale.setDefault(Locale.US);
		Scanner sc = new Scanner(System.in);

		DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

		System.out.println("Entre com os dados do aluguer");
		System.out.print("Modelo do carro: ");
		String carModel = sc.nextLine();
		System.out.print("Retirada: ");
		LocalDateTime start = LocalDateTime.parse(sc.nextLine(), fmt);
		System.out.print("Retorno: ");
		LocalDateTime finish = LocalDateTime.parse(sc.nextLine(), fmt);

		// Instanciar o carRental
		CarRental cr = new CarRental(start, finish, new Vehicle(carModel));

		System.out.print("Entre com preço por hora: ");
		double pricePerHour = sc.nextDouble();
		System.out.print("Entr com o preço por dia: ");
		double pricePerDay = sc.nextDouble();

		RentalService rentalService = new RentalService(pricePerHour, pricePerDay, new BrazilTaxService());

		rentalService.processInvoice(cr);

		System.out.println("Fatura");
		System.out.println("Pagaemento basico: " + String.format("%.2f", cr.getInvoice().getBasicPayment()));
		System.out.println("Imposto: " + String.format("%.2f", cr.getInvoice().getTax()));
		System.out.println("Pagemento total: " + String.format("%.2f", cr.getInvoice().getTotalPayment()));
		sc.close();
	}

}
