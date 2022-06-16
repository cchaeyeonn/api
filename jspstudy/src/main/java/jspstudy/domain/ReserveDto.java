//ReserveDto.java

package jspstudy.domain;

import java.util.Date;
//두개의 테이블이 들어가서 dto
public class ReserveDto {
	private int RIDX;
	private int RSIDX;
	private int M_MIDX;
	private String R_DELYN;
	private String R_DATE;
	private String R_APP;
	private String RS_stime;
	private String RS_ftime;
	private String RS_date;
	private String TENAME;
	private String TEGENDER;
	/*
	 * private int TEIDX; private String RS_DELYN;
	 */
	
	
	
	/*
	 * public int getTEIDX() { return TEIDX; } public void setTEIDX(int tEIDX) {
	 * TEIDX = tEIDX; } public String getRS_DELYN() { return RS_DELYN; } public void
	 * setRS_DELYN(String rS_DELYN) { RS_DELYN = rS_DELYN; }
	 */
	
	
	public String getTEGENDER() {
		return TEGENDER;
	}
	public void setTEGENDER(String tEGENDER) {
		TEGENDER = tEGENDER;
	}
	public String getTENAME() {
		return TENAME;
	}
	public void setTENAME(String tENAME) {
		TENAME = tENAME;
	}
	public String getRS_stime() {
		return RS_stime;
	}
	public void setRS_stime(String rS_stime) {
		RS_stime = rS_stime;
	}
	public String getRS_ftime() {
		return RS_ftime;
	}
	public void setRS_ftime(String rS_ftime) {
		RS_ftime = rS_ftime;
	}
	public String getRS_date() {
		return RS_date;
	}
	public void setRS_date(String rS_date) {
		RS_date = rS_date;
	}
	
	public int getRIDX() {
		return RIDX;
	}
	public void setRIDX(int rIDX) {
		RIDX = rIDX;
	}
	public int getRSIDX() {
		return RSIDX;
	}
	public void setRSIDX(int rSIDX) {
		RSIDX = rSIDX;
	}
	public int getM_MIDX() {
		return M_MIDX;
	}
	public void setM_MIDX(int m_MIDX) {
		M_MIDX = m_MIDX;
	}
	public String getR_DELYN() {
		return R_DELYN;
	}
	public void setR_DELYN(String r_DELYN) {
		R_DELYN = r_DELYN;
	}
	public String getR_DATE() {
		return R_DATE;
	}
	public void setR_DATE(String r_DATE) {
		R_DATE = r_DATE;
	}
	public String getR_APP() {
		return R_APP;
	}
	public void setR_APP(String r_APP) {
		R_APP = r_APP;
	}

}
