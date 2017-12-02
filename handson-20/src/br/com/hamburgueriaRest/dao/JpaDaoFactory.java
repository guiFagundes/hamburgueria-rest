package br.com.hamburgueriaRest.dao;

public class JpaDaoFactory {

	public static JpaDaoFactory instance = new JpaDaoFactory();
	
	private CervejaDao cervejaDao;
	private HamburguerDao hamburguerDao;
	private ClienteDao clienteDao;
	private ChopeDao chopeDao;
	

	private JpaDaoFactory() {}
		
	public static JpaDaoFactory getInstance(){
		return instance;
	}
	
	
	public CervejaDao getCervejaDao(){
		if(this.cervejaDao == null)
			this.cervejaDao = new CervejaDao();
		return this.cervejaDao;
	}
	
	public ChopeDao getChopeDao(){
		if(this.chopeDao == null)
			this.chopeDao = new ChopeDao();
		return this.chopeDao;
	}
	
	public HamburguerDao getHamburguerDao(){
		if(this.hamburguerDao == null)
			this.hamburguerDao = new HamburguerDao();
		return this.hamburguerDao;
	}
	
	public ClienteDao getClienteDao(){
		if(this.clienteDao == null)
			this.clienteDao = new ClienteDao();
		return this.clienteDao;
	}

}
