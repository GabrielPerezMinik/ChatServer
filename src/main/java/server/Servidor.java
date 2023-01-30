package server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Vector;

public class Servidor {

	private Vector<ConexionCliente> clientes;
	private ArrayList<String> nicks= new ArrayList<String>();
	private ServerSocket socketServidor;
	private int puerto;
	ConexionCliente conexionCliente;

	public Servidor(int puerto) {
		this.puerto = puerto;
		clientes = new Vector<>();
	}

	public void conectar() throws IOException {
		socketServidor = new ServerSocket(puerto);
	}

	public void desconectar() throws IOException {
		socketServidor.close();
	}

	public void enviarATodos(String mensaje) {

		for (ConexionCliente cliente : clientes) {
			cliente.enviar(mensaje);
		}
	}

	public void aceptarConexion() throws IOException {

		Socket socketCliente = socketServidor.accept();
		conexionCliente = new ConexionCliente(this, socketCliente);
		clientes.add(conexionCliente);
		conexionCliente.start();
		System.out.println("Nueva conexión.");
	}

	public void cortarCliente(ConexionCliente conexion) {
		clientes.remove(conexion);
		System.out.println("Conexión cerrada");
	}

	public boolean estaConectado() {
		return !socketServidor.isClosed();
	}

	public ConexionCliente getConexionCliente() {
		return conexionCliente;
	}

	public boolean Registronicks(String nick) {
		
		boolean pasar=false;
		
//		if(nicks.isEmpty()) {
//			nicks.add(nick);
//			pasar=true;
//			enviarATodos("::ok");
//			return pasar;
//		}
		
		for (int i = 0; i < nicks.size(); i++) {
			if(nicks.get(i).contains(nick)) {
				pasar=false;
			}
			else {
				nicks.add(nick);
				pasar=true;
				enviarATodos("::ok");
				return pasar;
			}
		}
		return pasar;
		
	}
	
}
