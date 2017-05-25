package com.coder.jsf.shiro.beans;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.LockedAccountException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;

@ManagedBean
@SessionScoped
public class ShiroYetkilendirme {

	private String kullaniciAdi;
	private String parola;
	private boolean rememberMe;
	
	public ShiroYetkilendirme() {
		// TODO Auto-generated constructor stub
	}

	public String getKullaniciAdi() {
		return kullaniciAdi;
	}

	public void setKullaniciAdi(String kullaniciAdi) {
		this.kullaniciAdi = kullaniciAdi;
	}

	public String getParola() {
		return parola;
	}

	public void setParola(String parola) {
		this.parola = parola;
	}
	
	public boolean isRememberMe() {
		return rememberMe;
	}

	public void setRememberMe(boolean rememberMe) {
		this.rememberMe = rememberMe;
	}

	public String girisiKontrolEt() {
		
		Subject currentUser = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken(kullaniciAdi, parola);
		token.setRememberMe(rememberMe);
		
		try{
            currentUser.login(token);
        } catch (UnknownAccountException uae ) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Giriş başarısız", "kullanıcı adınız yanlış"));
            return null;
        } catch (IncorrectCredentialsException ice ) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Giriş başarısız", "parolanız yanlış"));
            return null;
        } catch (LockedAccountException lae ) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Giriş başarısız", "Bu kullanıcı adı kilitli"));
            return null;
        } catch(AuthenticationException aex){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "Giriş başarısız", aex.toString()));
            return null;
        }
		
		if(kullaniciAdi.equals("yonetici"))
			return "admin.xhtml?faces-redirect=true";
		else
		return "bilgilendirme.xhtml?faces-redirect=true";
	}
	
	public String logout() {
		
		Subject currentUser = SecurityUtils.getSubject();
		
		try {
			currentUser.logout();
		} catch (Exception e) {}
		
		return "giris.xhtml?faces-redirect=true";
	}
}




