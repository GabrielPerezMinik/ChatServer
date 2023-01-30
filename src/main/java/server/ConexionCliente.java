package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Objects;

import javafx.scene.layout.VBox;
import ui.ControllerC;

public class ConexionCliente extends Thread {

	private Socket socket;
	private PrintWriter salida;
	private BufferedReader entrada;
	private Servidor servidor;
	

	

	public ConexionCliente(Servidor servidor, Socket socket) throws IOException {
		this.servidor = servidor;
		this.socket = socket;
		salida = new PrintWriter(socket.getOutputStream(), true);
		entrada = new BufferedReader(new InputStreamReader(socket.getInputStream()));

	}

	@Override
	public void run() {

		salida.println("Bienvenido al Chat");
		salida.println("Puedes enviar mensajes de texto");

		String mensaje = null;
		try {
			while (!((mensaje = entrada.readLine()) != null && !mensaje.equals(":bye"))) {
//				servidor.enviarATodos(mensaje);
//				enviar(mensaje);
				servidor.cortarCliente(this);
				salida.println("** ¡¡ Hasta Pronto !! **");
				entrada.close();
				salida.close();
				socket.close();
			}

		} catch (IOException ioe) {
			ioe.printStackTrace();
		}
	}

	public void enviar(String mensaje) {
		
		try {
			String apodo=buscarNick(mensaje);
			while (!(servidor.Registronicks(apodo))) {
				salida.println(":refuse <" + apodo +">");			
			}
			
			salida.println(mensaje);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		

	}

	public void recibirMensajeDelCliente(VBox vboxMessage) {
		
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				String mensajeDelServidor = "";
				while(socket.isConnected()) {
					try {
						 mensajeDelServidor = entrada.readLine();
						 
						 if(mensajeDelServidor.contains(":nickname"))  {
							 servidor.Registronicks(mensajeDelServidor);
						 }
						 
						ControllerC.addLebel(mensajeDelServidor, vboxMessage);						

					} catch (Exception e) {

					e.printStackTrace();
					System.out.println("Error al recibir un mensaje del Cliente");
					break;
					}
				}				
			}
		}).start();
		
	}
	
//	public void enviar(String mensaje) {
//		salida.println(mensaje);
//	}
	
	@Override
	public int hashCode() {
		return Objects.hash(socket);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		ConexionCliente other = (ConexionCliente) obj;
		return Objects.equals(socket.getLocalPort(), other.socket.getLocalPort());
	}
	
	public String buscarNick(String nick) { 
	        char[] charSearch = new char[nick.length()];
	        @SuppressWarnings("unused")
			String ni;
	        int e=0;
	        for(int i=9; i<nick.length(); i++){
	            charSearch[e]= nick.charAt(i);
	            e++;
	            }  
	        return ni= String.valueOf(charSearch.toString());
	        }
	
}
