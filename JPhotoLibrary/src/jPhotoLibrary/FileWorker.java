package jPhotoLibrary;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import org.h2.util.StringUtils;

class FileWorker {
	
	static String excludedFilesRegexPattern;
	
	public void scanDirectories() {		
		String[] directoriesToScan = JPhotoLibrary.foldersToScan();
		for (String directoryStr : directoriesToScan) {
			//check if the path is correct directory
			File directoryFile = new File(directoryStr);
			if (!directoryFile.exists()) {
				//TODO need to do something when directory from preferences is not valid
				break;
			}
			if (!directoryFile.isDirectory()) {
				//TODO the path is not directory
				break;
			}
			//actually scan thru directory
			try {
				Files.walkFileTree(directoryFile.toPath(), new JPL_FileVisitor());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}		
	}
	
	class JPL_FileVisitor implements FileVisitor<Path> {
		
		JPL_FileVisitor() {
			//initialize excludedFilesRegexPattern only once 
			if (StringUtils.isNullOrEmpty(excludedFilesRegexPattern)) {
				//file types contents symbols, that we need to escape for regex
				String escapedStr = excludedFilesTypesStr().replaceAll("([\\\\+*?\\[\\](){}|.^$])", "\\\\$1");
				//replace "," with "|" for regex (jpg|png) construction
				escapedStr = escapedStr.replace(",", "|");
				excludedFilesRegexPattern = "(?i:.*\\.(" + escapedStr + "))";
			}
		}
				
		@Override
		public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
			// TODO Auto-generated method stub
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
			if (file.getFileName().toString().matches(excludedFilesRegexPattern)) {
				//we skip system and auxiliary files				
				return FileVisitResult.CONTINUE;				
			}
			BasicFileAttributes fileAttrs = Files.readAttributes(file, BasicFileAttributes.class);
		    JPhotoLibrary.newFile(fileAttrs.size(), JPhotoLibrary.thisPC, file.toString(), file.getParent().toString(), fileAttrs.lastModifiedTime().toString()); 
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
			// TODO Auto-generated method stub
			return FileVisitResult.CONTINUE;
		}

		@Override
		public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
			// TODO Auto-generated method stub
			return FileVisitResult.CONTINUE;
		}
	
		String excludedFilesTypesStr() {
			String systemFilesTypes = "DLL,ICO,LNK,DRV,SYS,CAB,CUR,CPL,DMP,ICNS,DESKTHEMEPACK,HLP,SWP,MSP"
					+ ",REG,THEME,SCR,PRF,SCF,ANI,SDB,FPBF,WDGT,PFX,PROFILE,BASH_HISTORY,BASHRC,BASH_PROFILE"
					+ ",ELF,EDJ,GROUP,NB0,PCK,BCD,PROP,PWL,KEXT,VXD,IDX,NT,KEY,KBD,MSC,CNT,POL,HHC,HHK,MANIFEST"
					+ ",MDMP,ION,PDR,PREFPANE,ODEX,PNF,DESKLINK,MAPIMAIL,MYDOCS,SQM,ADM,MSSTYLES,SYS,MUI,HDMP"
					+ ",SAVER,HTT,PK2,SAVEDSEARCH,NFO,PPD,HPJ,ICL,MUM,WGZ,CGZ,CPI,KO,ETL,LOG2,LOG1,EVT,RMT,MI4"
					+ ",CRASH,CLB,ICONPACKAGE,NLS,RFW,WDF,MOBILECONFIG,JOB,MLC,EFI,WPH,EVTX,ADMX,DIAGCAB,WEBPNP"
					+ ",SBN,BLF,REGTRANS-MS,DAT,LST,SHD,SPL,HELP,DIT,ASEC,SHSH,LPD,SEARCHCONNECTOR-MS,LIBRARY-MS"
					+ ",HIV,PAT,SBF,CPR,ITEMDATA-MS,CAT,ZONE.IDENTIFIER,LOCKFILE,BIN,IUS,C32,AML,FOTA,2FS,IMG3"
					+ ",EFIRES,MTZ,H1S,DRPM,INF_LOC,EBD,CONFIGPROFILE,PROVISIONPROFILE,IDI,ESCOPY,SERVICE,FFA"
					+ ",FFL,FFO,FFX,INS,GRP,VGA,VGD,DSS,DEV,ANN,FTS,FTG,BMK,WER,SFCACHE,MSSTYLE,EMERALD"
					+ ",METADATA_NEVER_INDEX,BOM,XRM-MS,AOS,8XU,BIO,PANIC,MEM,IPTHEME,RCO,0,SO.0,MBR,ADML,CDMP"
					+ ",GMMP,DIAGCFG,DIAGPKG,PRINTEREXPORT,CI,LPD,CHG,SCHEMAS,IPOD,RC1,RC2,VX_,PPM,SCAP"
					+ ",AUTOMATICDESTINATIONS-MS,MOD,CHK,SDT,DFU,73U,NTFS,CPQ,IME,NBH,FX,IFW,SB,TDZ,KS,UCE"
					+ ",TRX_DLL,BUD,3FS,INTERNETCONNECT,NETWORKCONNECT,MUI_CCCD5AE0,TIMER,XFB,MBN,CAP,SPX,8CU"
					+ ",208,HCD,FL1,IM4P,CANNEDSEARCH,FTF,CUSTOMDESTINATIONS-MS,DEVICEMETADATA-MS,MMV,FIRM,CMO"
					+ ",SIN,TRASHES,PRO,THUMBNAILS,DVD,ITS,PID,PRT,STR,WPX,GRL,QVM,386,RUF,DYC,DLX,DTHUMB,BK1"
					+ ",BK2,PS1,FID,HSH,000,PS2,CHS,JPN,KOR,THA,CHT,LFS,PIT,ME,RS,DIMAX,ATAHD,IOPLIST,ADV,FLG"
					+ ",QKY,RVP,CM0013,TA";
			String settingsFilesTypes = "INI,CFG,PRF,ZAP,INF,MST,PLIST,RDF`,PRX,CSF,PSF,ICC,RDP,PROPERTIES,ICA"
					+ ",VMX,CONF,DBB,STYLE,THMX,THEMEPACK,WFC,STB,CTB,STE,ACROBATSECURITYSETTINGS,APPLICATION"
					+ ",APPREF-MS,C2R,CVA,OBI,UTZ,HME,CLG,RMSKIN,DCP,QRC,ASE,IDPP,PS1XML,MMP,WCX,MYCOLORS,VMC"
					+ ",AUX,VBOX,OSDX,INS,CNF,GMW,XTODVD,NGRR,FETCHMIRROR,TSCPROJ,WFP,KYS,GID,REG,XLB,FXP,OPS"
					+ ",CDT,WMZ,WMS,SET,ATN,ASL,MTF,DSW,ISS,EQP,EQL,DBG,FMT,TSK,SFO,VMX,AMS,SOL,INI,MSM,RDO"
					+ ",DS_STORE,BCMX,MLK,LOP,AHL,PRF,NVC,CFG,VMTM,SL,FCC,ICM,PROFIMAIL,INI,JWS,SRS,PVS,DRM"
					+ ",DOWNLOADHOST,MAILHOST,M2S,SKI,AHS,MSKN,HID,PMC,SW2,BGI,PIP,AST,TDF,OPT,EQF,A2M,CHL"
					+ ",THE,NDC,CLR,IHW,PTF,RPK,TSZ,BLT,ICD,MPT,TSM,NWV,MSN,GWS,XTREME,RTS,GROWLTICKET,BLOB"
					+ ",SSS,UIS,SLT,DOLPHINVIEW,DIRECTORY,EUM,VNC,SIF,OVPN,QF,XPADDERCONTROLLER,ATZ,SKZ,CUI"
					+ ",AVS,PIE,FWT,ND,RPS,BCP,PROPDESC,TWC,EFTX,ALX,OEM,USERPROFILE,VCPREF,TRX,BAU,CAMP,IIP"
					+ ",SKN,DDF,GRD,ISS,EXE4J,MCL,SBV,QTP,MOF,SPF,QSS,P2M,LVF,LVA,PMP,PC3,CPX,PMJ,GXT,GIN,AIT"
					+ ",BKS,RLL,FD,FT,NJI,XCU,RFQ,ITT,WCZ,PKG,ACV,ODC,UDCX,MNU,MNS,CUIX,SETTINGS,RULESET,RCT"
					+ ",PROPS,VSPROPS,VSSETTINGS,SKIN,OSS,ACB,BTSEARCH,S2ML,PCTL,ONETOC2,NCFG,CHX,DSD,ATC,PGP"
					+ ",XTP,DCL,FMP,ACO,ACT,AHU,ALV,AXT,BLW,IRS,GPS,VLT,DCST,FLST,AIA,JDF,PSC1,FTH,ASW,CONF"
					+ ",ICURSORFX,ARG,FFX,LRTEMPLATE,VMCX,HT,BRG,KDS,PRFPSET,PXB,CPG,A2THEME,RAD,PROFILE,AVS"
					+ ",VIMRC,VIM,DUCK,SGT,TPF,CSAPLAN,XUI,XUR,SPFX,CMP,SMT,PRM,UCT,RCF,VMXF,XVM,MXSKIN,CEX"
					+ ",PREF,ATH,SED,ENS,ENZ,VPS,VQC,ADPP,GTKRC,L4D,LOOK,MASK,ARS,AOM,PSP,BS7,BITPIM,CSKIN,MGK"
					+ ",COS,COSTYLE,COPRESET,EHI,LCC,WVE,PIO,TSI,ZON,SQD,IPCC,ICST,ASEF,SETTINGCONTENT-MS"
					+ ",EXPORTEDUI,OFFICEUI,QAT,ASP,IKMP,FC,VSTPRESET,DVTCOLORTHEME,PJS,MDS,WC,PRO5TEMPLATE"
					+ ",A7P,PDADJ,CPS,CPTM,DEFT,NPS,NSX,RDW,AIP,RPROJ,STD,COMP,FLW,CMATE,XMS,VCOMPS,DOK,PDP"
					+ ",DTSCONFIG,FVP,TVC,SYNW-PROJ,WIF,DAR,IPYNB,LH3D,TVTEMPLATE,POLICY,WME,DR5,DSF,OMS,NVP"
					+ ",PR,CPDX,EWPRJ,EMM,FTP,SCPRESETS,MOTI,MOEF,MOTN,MOTR,NP4,OTPU,VTPR,3DL,MOBIRISE,SCRIBE"
					+ ",SPP,HD3D,QXW,DRP,KEY,ISP,VSW,FXB,CNF,PH,NTC,VUE,VPH,SZ,PRF,ATF,CWF,MSF,PEN,ZON,EXP,FNC"
					+ ",CGR,PRS,ARP,PRF,IIP,CHA,FEV,RPB,AEA,XGS,LBU,ACRODATA,JSF,OCE,BXX,ASWS,ASWCS,IAF,SKIN"
					+ ",THEME,GTP,DINFO,DICPROOF,MATERIAL,MST,IIT,XST,ZPI,XPL,TYPEIT4ME,GPS,BOOT,XDR,PHB,IX,T3D"
					+ ",FAT,MPDCONF,RNX,AXP,RMS,LAY,LAY,RB4,MNU,Q2D,MXCS,MXFR,FRC,FRR,TST,PWS,MGTHEME,USER,VCW"
					+ ",MDP,LILY,SRF,OPTS,S2QL,S2QH,MPW,AWS,CUS,FDC,ACF,ADO,AMP,ASV,AVS,EAP,IROS,MNU,SHH,TPL"
					+ ",ACBL,INMS,LNST,PRST,SEQU,FMOD,WORKSPACE,CLR,ENV,FLT,WPS,CST,CFG,FTH,XLB,THEME,SCACFG"
					+ ",SCPCFG,ELM,PERFMONCFG,RESMONCFG,PSY,MCW,WZCONFIG,KBD,ACW,WLVS,PML,TTS,LXSOPT,ACB,TMTHEME"
					+ ",QVT,QVPP,PXG,ALL,AVE,DXLS,CFG,STORYISTTHEME,AWCAV,PAPERS,MXS,MSW,PAL,CYBERDUCKPROFILE"
					+ ",CYBERDUCKLICENSE,SPJ,STT,TLO,CSPLAN,STARTUPINFO,CFG,LYT,VBX6SETTINGS,UPF,CPR,TPARK,NKP"
					+ ",CPF,OBT,FTPQUOTA,GCSX,GQSX,MMDC,SSL2,SSL,RASKINPLACE,WORK,SCH,BIN,PRF,ZPF,DSX,LRSMCOL"
					+ ",RWSTYLE,TERMINAL,DUN,OTZ,HDT,USF,DPV,ANTISPAM5,EPR,P3E,ZVT,CMMTHEME,SSS,FAVORITEMETADATA"
					+ ",SEESTYLE,QPX,MAL,BCS,BCP,JKM,CTBODYFITTING,JOY,ASK,VMPL,DXP,AIU,SNX,GVIMRC,IKF,SKN,ABS"
					+ ",NPFX,KSF,FRAMES,FBT,LFO,EXAMPLE,VEGASWINDOWLAYOUT,RES,PFS,SSIS,SBX,FAN,SECURITYSETTINGS"
					+ ",COLOURSCHEME,STIP,STFX,SCONF,PTG,PXIP,PXS,PXLS,PTP,PQ,XCCOLORTHEME,XCSCHEME,AGTEMPLATE"
					+ ",UGR,ACS2,ACS3,TCLS,TCCFGEXTENDER,CXP,LIGHTINGLIBRARY,BRUSHVARIANT,A7L,A7D,PDLCP"
					+ ",SUBLIME-MENU,SUBLIME-OPTIONS,SUBLIME-SETTINGS,SUBLIME-THEME,EYETVSCHED,EYETVP,CWY,CBOARD"
					+ ",H2P,DFT,FST,NL,AIP,KUIP,DSC,MULIB,CENON,RPV,CROP,CLKP,HPR,SIF,STSKIN,PMSETTINGS,RDR,3DZ"
					+ ",EMMT,WFW,HOW2,MAGIWOL,XCT,PBS,LMC,TEE,MOGRT,TIM,ECFG,S2ARR,SDLTPL,FORM,NPT,PBP,MCOLOR"
					+ ",ACELIVE,BSXP,BSXC,TFX,RHR,P7E,OTW,OTWU,OTP,OTM,OTMU,ANIMEEXPORT,MOHOEXPORT,MOHOSTYLE"
					+ ",MOHOACTION,MOHOBRUSH,AMGP,PSPSCRIPT,SXCU,AGG,JAP,Q5Q,CSG,MIND,CDP,LSPROJ,PDSPRJ,SBS"
					+ ",GVSWATCH,QDF,RGRID,STYX,IPF,CTF,EWW,EFT,CON,PAK,RPE,FSPY,AGG,SMP,XIZ,FLP,SAV,ENV,IDF,KYB"
					+ ",WPP,PREF,CM,XEP,XEM,XES,XET,XEV,MGM,XWK,SET,RRR,RB,PLS,LANG,NTS,DPS,8ST,IGR,X4K,ARL,EV3"
					+ ",STF,256,PRA,LGT,ETFF,LYT,API,PDFS,VNI,RDI,FM3,OLK14PREF,AOIS,RML,MAKE,STY,CASE,LOADERS"
					+ ",IMMODULES,GDIAGRAMSTYLE,CF,LAYOUT,LXCP,BCS,ENP,STC,VMAC,VMBA,VMT,TPL,OPTIONS,CONTROLS"
					+ ",TLL,ABS,SSCS,IDDX,TGW,CIS,FE_LAUNCH,ONETOC,INJB,ITI,PPV,OTH,OIS,ODT,OIF,OFP,ANIMESTYLE"
					+ ",ANIMEACTION,ANIMEBRUSH,MSH1XML,Q9Q,AUTOSAVE.CDP,RTI";
			String excludedFilesTypesStr = systemFilesTypes + "," + settingsFilesTypes;
			return excludedFilesTypesStr;
		}
		
	}
	
	static public String hashFile(String fileStr) throws HashGenerationException {
		try (FileInputStream inputStream = new FileInputStream(fileStr)) {
	        MessageDigest digest = MessageDigest.getInstance("SHA-1");
	 
	        byte[] bytesBuffer = new byte[1024];
	        int bytesRead = -1;
	 
	        while ((bytesRead = inputStream.read(bytesBuffer)) != -1) {
	            digest.update(bytesBuffer, 0, bytesRead);
	        }
	 
	        byte[] hashedBytes = digest.digest();
	 
	        return convertByteArrayToHexString(hashedBytes);
	    } catch (NoSuchAlgorithmException | IOException ex) {
	        throw new HashGenerationException(
	                "Could not generate hash from file", ex);
	    }		   
	}
	
	static public String convertByteArrayToHexString(byte[] arrayBytes) {
	    StringBuffer stringBuffer = new StringBuffer();
	    for (int i = 0; i < arrayBytes.length; i++) {
	        stringBuffer.append(Integer.toString((arrayBytes[i] & 0xff) + 0x100, 16)
	                .substring(1));
	    }
	    return stringBuffer.toString();
	}
		
}