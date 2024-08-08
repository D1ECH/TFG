package Dispositivos;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.HashMap;
import java.util.Map;

public class Dispositivoo {
    private int id;
    private int numVulnerabilidades;
    private boolean tieneCertificado;
    private LocalDate ultimaRevision;
    private int tiempoFuncionamiento;
    private int actualizacionesFirmware;
    private int frecuenciaComunicacion;
    private int historialFallos;
    private Map<String, Double> pesos;

    private String tipo;
	private int reputacionInicial;
	private String estadoActual;

    public Dispositivoo(int id, int numVulnerabilidades, boolean tieneCertificado, LocalDate ultimaRevision, 
                       int tiempoFuncionamiento, int actualizacionesFirmware, int frecuenciaComunicacion, int historialFallos) {
        this.id = id;
        this.numVulnerabilidades = numVulnerabilidades;
        this.tieneCertificado = tieneCertificado;
        this.ultimaRevision = ultimaRevision;
        this.tiempoFuncionamiento = tiempoFuncionamiento;
        this.actualizacionesFirmware = actualizacionesFirmware;
        this.frecuenciaComunicacion = frecuenciaComunicacion;
        this.historialFallos = historialFallos;
        this.pesos = new HashMap<>();
        
        // Pesos obtenidos del método AHP
        this.pesos.put("numVulnerabilidades", 0.382);
        this.pesos.put("tieneCertificado", 0.167);
        this.pesos.put("ultimaRevision", 0.068);
        this.pesos.put("tiempoFuncionamiento", 0.033);
        this.pesos.put("actualizacionesFirmware", 0.167);
        this.pesos.put("frecuenciaComunicacion", 0.068);
        this.pesos.put("historialFallos", 0.167);
    }


    // Métodos getters y setters, si son necesarios
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getNumVulnerabilidades() {
        return numVulnerabilidades;
    }

    public void setNumVulnerabilidades(int numVulnerabilidades) {
        this.numVulnerabilidades = numVulnerabilidades;
    }

    public boolean isTieneCertificado() {
        return tieneCertificado;
    }

    public void setTieneCertificado(boolean tieneCertificado) {
        this.tieneCertificado = tieneCertificado;
    }

    public LocalDate getUltimaRevision() {
        return ultimaRevision;
    }

    public void setUltimaRevision(LocalDate ultimaRevision) {
        this.ultimaRevision = ultimaRevision;
    }

    public int getTiempoFuncionamiento() {
        return tiempoFuncionamiento;
    }

    public void setTiempoFuncionamiento(int tiempoFuncionamiento) {
        this.tiempoFuncionamiento = tiempoFuncionamiento;
    }

    public int getActualizacionesFirmware() {
        return actualizacionesFirmware;
    }

    public void setActualizacionesFirmware(int actualizacionesFirmware) {
        this.actualizacionesFirmware = actualizacionesFirmware;
    }

    public int getFrecuenciaComunicacion() {
        return frecuenciaComunicacion;
    }

    public void setFrecuenciaComunicacion(int frecuenciaComunicacion) {
        this.frecuenciaComunicacion = frecuenciaComunicacion;
    }

    public int getHistorialFallos() {
        return historialFallos;
    }

    public void setHistorialFallos(int historialFallos) {
        this.historialFallos = historialFallos;
    }

    public Map<String, Double> getPesos() {
        return pesos;
    }

    public void setPesos(Map<String, Double> pesos) {
        this.pesos = pesos;
    }


    // Calcular la confianza de un dispositivo
    public double calcularConfianza() {
        double sumaPonderada = 0.0;
        double sumaPesos = 0.0;

        // Evaluar cada atributo y su peso
        sumaPonderada += evaluarVulnerabilidades() * pesos.get("numVulnerabilidades");
        sumaPonderada += (tieneCertificado ? 1 : 0) * pesos.get("tieneCertificado");
        sumaPonderada += evaluarUltimaRevision() * pesos.get("ultimaRevision");
        sumaPonderada += evaluarTiempoFuncionamiento() * pesos.get("tiempoFuncionamiento");
        sumaPonderada += evaluarActualizacionesFirmware() * pesos.get("actualizacionesFirmware");
        sumaPonderada += evaluarFrecuenciaComunicacion() * pesos.get("frecuenciaComunicacion");
        sumaPonderada += evaluarHistorialFallos() * pesos.get("historialFallos");

        sumaPesos = pesos.values().stream().mapToDouble(Double::doubleValue).sum();

        return sumaPonderada / sumaPesos; // Normalización
    }

    private double evaluarVulnerabilidades() {
        if (numVulnerabilidades == 0) return 1.0;
        if (numVulnerabilidades <= 5) return 0.8;
        if (numVulnerabilidades <= 10) return 0.5;
        return 0.2;
    }

    private double evaluarUltimaRevision() {
        long diasDesdeUltimaRevision = ChronoUnit.DAYS.between(ultimaRevision, LocalDate.now());
        if (diasDesdeUltimaRevision <= 30) return 1.0;
        if (diasDesdeUltimaRevision <= 90) return 0.8;
        if (diasDesdeUltimaRevision <= 180) return 0.5;
        return 0.2;
    }

    private double evaluarTiempoFuncionamiento() {
        if (tiempoFuncionamiento > 1000) return 1.0;
        if (tiempoFuncionamiento > 500) return 0.8;
        return 0.5;
    }

    private double evaluarActualizacionesFirmware() {
        if (actualizacionesFirmware > 10) return 1.0;
        if (actualizacionesFirmware > 5) return 0.8;
        return 0.5;
    }

    private double evaluarFrecuenciaComunicacion() {
        if (frecuenciaComunicacion > 100) return 1.0;
        if (frecuenciaComunicacion > 50) return 0.8;
        return 0.5;
    }

    private double evaluarHistorialFallos() {
        if (historialFallos == 0) return 1.0;
        if (historialFallos <= 2) return 0.8;
        return 0.5;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// TO-STRING
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 * 
	@Override
	public String toString() {
		return "Thing [thingID=" + thingID + ", lastSupervision=" + lastSupervision + ", numPastVulns="
				+ numPastVulns + ", securityCertification=" + securityCertification + "]";
	} */
	
	/* (non-Javadoc)
	 * REWRITE
	 * @see java.lang.Object#toString()
	 * */
	@Override
	public String toString() {
		return "" + id + " --> [" + ultimaRevision.toString() + " | " + numVulnerabilidades + " | " + tieneCertificado + " | " + tiempoFuncionamiento + " | " + actualizacionesFirmware + " | " 
        + frecuenciaComunicacion + " | " + historialFallos + " | " + tipo + " | " + reputacionInicial + " | " + estadoActual + "]";
	}
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// HASHCODE
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + id;
		return result;
	}
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	// EQUALS
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof Dispositivoo)) {
			return false;
		}
		Dispositivoo other = (Dispositivoo) obj;
		if (id != other.id) {
			return false;
		}
		return true;
	}
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

}
