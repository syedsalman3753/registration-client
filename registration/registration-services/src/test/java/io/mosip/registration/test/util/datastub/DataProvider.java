package io.mosip.registration.test.util.datastub;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import io.mosip.commons.packet.dto.packet.AuditDto;
import io.mosip.commons.packet.dto.packet.BiometricsException;
import io.mosip.commons.packet.dto.packet.SimpleDto;
import io.mosip.registration.constants.RegistrationConstants;
import io.mosip.registration.dto.OSIDataDTO;
import io.mosip.registration.dto.RegistrationDTO;
import io.mosip.registration.dto.RegistrationMetaDataDTO;
import io.mosip.registration.dto.biometric.BiometricExceptionDTO;
import io.mosip.registration.dto.biometric.BiometricInfoDTO;
import io.mosip.registration.dto.biometric.FaceDetailsDTO;
import io.mosip.registration.dto.biometric.FingerprintDetailsDTO;
import io.mosip.registration.dto.biometric.IrisDetailsDTO;
import io.mosip.registration.dto.packetmanager.BiometricsDto;
import io.mosip.registration.dto.packetmanager.DocumentDto;
import io.mosip.registration.dto.schema.UiFieldDTO;
import io.mosip.registration.enums.FlowType;
import io.mosip.registration.exception.RegBaseCheckedException;

public class DataProvider {

	public static final String PERMANANENT = "Permananent";
	public static final String THUMB_JPG = "/thumb.jpg";
	private static final String APPLICANT ="applicant";

	private DataProvider() {

	}

	public static byte[] getImageBytes(String filePath) throws RegBaseCheckedException {
		filePath = "/dataprovider".concat(filePath);

		try {
			InputStream file = DataProvider.class.getResourceAsStream(filePath);
			byte[] bytesArray = new byte[(int) file.available()];
			file.read(bytesArray);
			file.close();

			return bytesArray;
		} catch (IOException ioException) {
			throw new RegBaseCheckedException(RegistrationConstants.SERVICE_DATA_PROVIDER_UTIL,
					"Unable to read the Image bytes", ioException);
		}
	}

	public static RegistrationDTO getPacketDTO() throws RegBaseCheckedException {
		RegistrationDTO registrationDTO = new RegistrationDTO();
		registrationDTO.setAuditDTOs(DataProvider.getAuditDTOs());
		registrationDTO.setOsiDataDTO(DataProvider.getOsiDataDTO());
		registrationDTO.setPreRegistrationId("PEN1345T");
		registrationDTO.setRegistrationId("10011100110016320190307151917");

		//registrationDTO.setDemographicDTO(DataProvider.getDemographicDTO());
		registrationDTO.setDemographics(new HashMap<String, Object>());
		registrationDTO.setBiometrics(getBiometricDTO());
		HashMap<String, Object> selectionListDTO=new HashMap<>();
		//registrationDTO.setSelectionListDTO(selectionListDTO);
		registrationDTO.setSelectedLanguagesByApplicant(Arrays.asList("eng", "ara", "fra"));
		registrationDTO.setProcessId("NEW");
		return registrationDTO;

	}

	public static RegistrationDTO getFilledPacketDTO(List<String> selectedLanguages) throws RegBaseCheckedException {
		RegistrationDTO registrationDTO = new RegistrationDTO();
		registrationDTO.setAuditDTOs(DataProvider.getAuditDTOs());
		registrationDTO.setOsiDataDTO(DataProvider.getOsiDataDTO());
		registrationDTO.setPreRegistrationId("PEN1345T");
		registrationDTO.setRegistrationId("10011100110016320190307151917");
		registrationDTO.setFlowType(FlowType.NEW);
		registrationDTO.setAppId("10011100110016320190307151917");
		registrationDTO.setProcessId("NEW");

		registrationDTO.setDemographics(getDemographicData(selectedLanguages));
		registrationDTO.setDocuments(getDocumentDetailsDTO());
		registrationDTO.setBiometrics(buildApplicantBiometrics());
		registrationDTO.setBiometricExceptions(buildExceptionBiometrics());
		registrationDTO.setOfficerBiometrics(DataProvider.buildBiometricDTO());
//		registrationDTO.setBiometricDTO(DataProvider.getBiometricDTO());
		//HashMap<String, Object> selectionListDTO=new HashMap<>();
		//registrationDTO.setSelectionListDTO(selectionListDTO);
		registrationDTO.setSelectedLanguagesByApplicant(selectedLanguages);
		return registrationDTO;

	}

	private static Map<String, BiometricsException> buildExceptionBiometrics() {
		Map<String, BiometricsException> biometricExceptions = new HashMap<>();
		BiometricsException exception = new BiometricsException("iris", "rightEye", "Due to accident", PERMANANENT, APPLICANT);
		biometricExceptions.put("applicant_rightEye", exception);
		return biometricExceptions;
	}

	private static Map<String, BiometricsDto> buildApplicantBiometrics() {
		Map<String, BiometricsDto> applicantBiometrics = new HashMap<>();
		BiometricsDto biometric = new BiometricsDto("leftEye", "leftEye".getBytes(), 90);
		biometric.setSignature("signature");
		biometric.setNumOfRetries(1);
		biometric.setSdkScore(90);
		biometric.setForceCaptured(false);
		biometric.setSpecVersion("1.2.0");
		applicantBiometrics.put("applicant_leftEye", biometric);
		return applicantBiometrics;
	}

	private static List<BiometricsDto> buildBiometricDTO() {
		List<BiometricsDto> biometrics = new ArrayList<>();
		BiometricsDto biometric = new BiometricsDto("leftIndex", "leftIndex".getBytes(), 90);
		biometric.setSignature("signature");
		biometric.setNumOfRetries(1);
		biometric.setSdkScore(90);
		biometric.setForceCaptured(false);
		biometric.setSpecVersion("1.2.0");
		biometrics.add(biometric);
		return biometrics;
	}

	private static Map<String, BiometricsDto> getBiometricDTO() throws RegBaseCheckedException {
		BiometricsDto biometrics1 = new BiometricsDto("leftMiddle", "leftMiddle".getBytes(), 90.0);
		biometrics1.setModalityName(RegistrationConstants.FINGERPRINT_UPPERCASE);
		BiometricsDto biometrics2 = new BiometricsDto("leftRing", "leftRing".getBytes(), 90.0);
		biometrics2.setModalityName(RegistrationConstants.FINGERPRINT_UPPERCASE);
		BiometricsDto biometrics3 = new BiometricsDto("leftEye", "leftEye".getBytes(), 90.0);
		biometrics3.setModalityName(RegistrationConstants.IRIS);
		BiometricsDto biometrics4 = new BiometricsDto("rightLittle", "rightLittle".getBytes(), 90.0);
		biometrics4.setModalityName(RegistrationConstants.FINGERPRINT_UPPERCASE);
		BiometricsDto biometrics5 = new BiometricsDto("face", "face".getBytes(), 90.0);
		biometrics5.setModalityName(RegistrationConstants.FACE);
		
		Map<String, BiometricsDto> applicantBiometrics = new HashMap<>();
		applicantBiometrics.put("applicant_leftMiddle", biometrics1);
		applicantBiometrics.put("applicant_leftRing", biometrics2);
		applicantBiometrics.put("applicant_leftEye", biometrics3);
		applicantBiometrics.put("applicant_rightLittle", biometrics4);
		applicantBiometrics.put("applicant_face", biometrics5);
		return applicantBiometrics;
	}

	private static BiometricInfoDTO buildBioMerticDTO(String persontype) throws RegBaseCheckedException {
		BiometricInfoDTO biometricInfoDTO = new BiometricInfoDTO();

		if (persontype.equalsIgnoreCase(DataProvider.APPLICANT)) {
			biometricInfoDTO.setFingerprintDetailsDTO(DataProvider.getFingerprintDetailsDTO(persontype));
			biometricInfoDTO.setBiometricExceptionDTO(DataProvider.getExceptionFingerprintDetailsDTO());
			biometricInfoDTO.setIrisDetailsDTO(DataProvider.getIrisDetailsDTO());
			biometricInfoDTO.setBiometricExceptionDTO(DataProvider.getExceptionIrisDetailsDTO());
			biometricInfoDTO.setFace(DataProvider.getFaceDTO());			
			biometricInfoDTO.setExceptionFace(DataProvider.getExceptionFaceDTO(biometricInfoDTO));
		} else if (persontype.equalsIgnoreCase("officer")) {
			biometricInfoDTO.setIrisDetailsDTO(DataProvider.getIrisDetailsDTO());
			biometricInfoDTO.setFace(DataProvider.getFaceDTO());
			biometricInfoDTO.setExceptionFace(DataProvider.getExceptionFaceDTO(biometricInfoDTO));
		} else {
			biometricInfoDTO.setBiometricExceptionDTO(DataProvider.getExceptionFingerprintDetailsDTO());
			biometricInfoDTO.setFingerprintDetailsDTO(DataProvider.getFingerprintDetailsDTO(persontype));
			biometricInfoDTO.setIrisDetailsDTO(DataProvider.getIrisDetailsDTO());
			biometricInfoDTO.setFace(DataProvider.getFaceDTO());
			biometricInfoDTO.setExceptionFace(DataProvider.getExceptionFaceDTO(biometricInfoDTO));
		}
		return biometricInfoDTO;
	}
	
	private static FaceDetailsDTO getFaceDTO() throws RegBaseCheckedException{
		FaceDetailsDTO faceDetailsDTO=new FaceDetailsDTO();
		faceDetailsDTO.setFace(DataProvider.getImageBytes("/applicantPhoto.jpg"));
		faceDetailsDTO.setPhotographName("ApplicantPhoto.jpg");
		faceDetailsDTO.setQualityScore(89.0);
		faceDetailsDTO.setNumOfRetries(0);
		return faceDetailsDTO;
	}
	private static FaceDetailsDTO getExceptionFaceDTO(BiometricInfoDTO biometricInfoDTO) throws RegBaseCheckedException{
		FaceDetailsDTO exceptionFaceDetailsDTO=new FaceDetailsDTO();
		exceptionFaceDetailsDTO.setFace(DataProvider.getImageBytes("/applicantPhoto.jpg"));
		exceptionFaceDetailsDTO.setPhotographName("ExceptionPhoto.jpg");
		exceptionFaceDetailsDTO.setQualityScore(89.0);
		exceptionFaceDetailsDTO.setNumOfRetries(1);
		biometricInfoDTO.setHasExceptionPhoto(true);
		return exceptionFaceDetailsDTO;
	}

	private static List<FingerprintDetailsDTO> getFingerprintDetailsDTO(String personType)
			throws RegBaseCheckedException {
		List<FingerprintDetailsDTO> fingerList = new ArrayList<>();

		if (personType.equals(DataProvider.APPLICANT)) {
			FingerprintDetailsDTO fingerprint = DataProvider.buildFingerPrintDetailsDTO(DataProvider.THUMB_JPG,
					"BothThumbs.jpg", 85.0, false, "thumbs", 0);
			fingerprint.getSegmentedFingerprints().add(DataProvider.buildFingerPrintDetailsDTO(DataProvider.THUMB_JPG,
					"rightThumb.jpg", 80.0, false, "rightThumb", 2));
			fingerList.add(fingerprint);

			fingerprint = DataProvider.buildFingerPrintDetailsDTO(DataProvider.THUMB_JPG, "LeftPalm.jpg", 80.0, false,
					"leftSlap", 3);
			fingerprint.getSegmentedFingerprints().add(DataProvider.buildFingerPrintDetailsDTO(DataProvider.THUMB_JPG,
					"leftIndex.jpg", 80.0, false, "leftIndex", 3));
			fingerprint.getSegmentedFingerprints().add(DataProvider.buildFingerPrintDetailsDTO(DataProvider.THUMB_JPG,
					"leftMiddle.jpg", 80.0, false, "leftMiddle", 1));
			fingerprint.getSegmentedFingerprints().add(DataProvider.buildFingerPrintDetailsDTO(DataProvider.THUMB_JPG,
					"leftRing.jpg", 80.0, false, "leftRing", 2));
			fingerprint.getSegmentedFingerprints().add(DataProvider.buildFingerPrintDetailsDTO(DataProvider.THUMB_JPG,
					"leftLittle.jpg", 80.0, false, "leftLittle", 0));
			fingerList.add(fingerprint);

			fingerprint = DataProvider.buildFingerPrintDetailsDTO(DataProvider.THUMB_JPG, "RightPalm.jpg", 95.0, false,
					"rightSlap", 2);
			fingerprint.getSegmentedFingerprints().add(DataProvider.buildFingerPrintDetailsDTO(DataProvider.THUMB_JPG,
					"rightIndex.jpg", 80.0, false, "rightIndex", 3));
			fingerprint.getSegmentedFingerprints().add(DataProvider.buildFingerPrintDetailsDTO(DataProvider.THUMB_JPG,
					"rightMiddle.jpg", 80.0, false, "rightMiddle", 1));
			fingerprint.getSegmentedFingerprints().add(DataProvider.buildFingerPrintDetailsDTO(DataProvider.THUMB_JPG,
					"rightRing.jpg", 80.0, false, "rightRing", 2));
			fingerprint.getSegmentedFingerprints().add(DataProvider.buildFingerPrintDetailsDTO(DataProvider.THUMB_JPG,
					"rightLittle.jpg", 80.0, false, "rightLittle", 0));
			fingerList.add(fingerprint);
		} else {
			fingerList.add(DataProvider.buildFingerPrintDetailsDTO(DataProvider.THUMB_JPG, personType + "LeftThumb.jpg", 0, false,
					"leftThumb", 0));
		}

		return fingerList;
	}

	private static FingerprintDetailsDTO buildFingerPrintDetailsDTO(String imageLoc, String fingerprintImageName,
			double qualityScore, boolean isForceCaptured, String fingerType, int numRetry)
			throws RegBaseCheckedException {
		FingerprintDetailsDTO fingerprintDetailsDTO = new FingerprintDetailsDTO();
		fingerprintDetailsDTO.setFingerPrint(DataProvider.getImageBytes(imageLoc));
		fingerprintDetailsDTO.setFingerprintImageName(fingerprintImageName);
		fingerprintDetailsDTO.setQualityScore(qualityScore);
		fingerprintDetailsDTO.setForceCaptured(isForceCaptured);
		fingerprintDetailsDTO.setFingerType(fingerType);
		fingerprintDetailsDTO.setNumRetry(numRetry);
		fingerprintDetailsDTO.setSegmentedFingerprints(new ArrayList<>());
		return fingerprintDetailsDTO;
	}

	private static List<BiometricExceptionDTO> getExceptionFingerprintDetailsDTO() {
		List<BiometricExceptionDTO> fingerExcepList = new ArrayList<>();

		fingerExcepList.add(DataProvider.buildBiometricExceptionDTO("fingerprint", "LeftThumb", "Due to accident",
				DataProvider.PERMANANENT));
		return fingerExcepList;
	}

	private static BiometricExceptionDTO buildBiometricExceptionDTO(String biometricType, String missingBiometric,
			String reason, String exceptionType) {
		BiometricExceptionDTO biometricExceptionDTO = new BiometricExceptionDTO();
		biometricExceptionDTO.setBiometricType(biometricType);
		biometricExceptionDTO.setMissingBiometric(missingBiometric);
		biometricExceptionDTO.setReason(reason);
		biometricExceptionDTO.setExceptionType(exceptionType);
		return biometricExceptionDTO;
	}

	private static List<IrisDetailsDTO> getIrisDetailsDTO() throws RegBaseCheckedException {
		List<IrisDetailsDTO> irisList = new ArrayList<>();
		irisList.add(DataProvider.buildIrisDetailsDTO("/eye.jpg", "LeftEye.jpg", "leftEye", false, 79.0));

		return irisList;
	}

	private static IrisDetailsDTO buildIrisDetailsDTO(String iris, String irisImageName, String irisType,
			boolean isForcedCaptured, double qualityScore) throws RegBaseCheckedException {
		IrisDetailsDTO irisDetailsDTO = new IrisDetailsDTO();
		irisDetailsDTO.setIris(DataProvider.getImageBytes(iris));
		irisDetailsDTO.setIrisImageName(irisImageName);
		irisDetailsDTO.setIrisType(irisType);
		irisDetailsDTO.setForceCaptured(isForcedCaptured);
		irisDetailsDTO.setQualityScore(qualityScore);
		irisDetailsDTO.setNumOfIrisRetry(2);
		return irisDetailsDTO;
	}

	private static List<BiometricExceptionDTO> getExceptionIrisDetailsDTO() {
		LinkedList<BiometricExceptionDTO> irisExcepList = new LinkedList<>();
		irisExcepList
				.add(DataProvider.buildBiometricExceptionDTO("iris", "RightEye", "By birth", DataProvider.PERMANANENT));

		return irisExcepList;
	}

	/*private static DemographicDTO getDemographicDTO() throws RegBaseCheckedException {
		DemographicDTO demographicDTO = new DemographicDTO();

		demographicDTO.setDemographicInfoDTO(DataProvider.getDemoInLocalLang());
		getDocumentDetailsDTO(demographicDTO.getDemographicInfoDTO().getIdentity(),
				new RegistrationDTO().getDocuments());
		return demographicDTO;
	}*/

	private static Map<String, Object> getDemographicData(List<String> selectedLanguages) {
		Map<String, Object> demographicMap = new HashMap<>();
		demographicMap.put("fullName",new ArrayList<SimpleDto>());
		demographicMap.put("dateOfBirth", "2018/01/01");
		demographicMap.put("gender", "MLE");
		demographicMap.put("addressLine1", new ArrayList<SimpleDto>());
		demographicMap.put("addressLine2", new ArrayList<SimpleDto>());
		demographicMap.put("region", new ArrayList<SimpleDto>());
		demographicMap.put("province", new ArrayList<SimpleDto>());
		demographicMap.put("city", new ArrayList<SimpleDto>());
		demographicMap.put("postalCode", "1232323");
		demographicMap.put("email", "test@test.com");
		demographicMap.put("phone", "222222222");

		selectedLanguages.forEach( lang -> {
			((List)demographicMap.get("fullName")).add(new SimpleDto(lang, "John"));
			((List)demographicMap.get("addressLine1")).add(new SimpleDto(lang, "address1"));
			((List)demographicMap.get("addressLine2")).add(new SimpleDto(lang, "address2"));
			((List)demographicMap.get("region")).add(new SimpleDto(lang, "India"));
			((List)demographicMap.get("province")).add(new SimpleDto(lang, "karnataka"));
			((List)demographicMap.get("city")).add(new SimpleDto(lang, "Bangalore"));
		});

		return demographicMap;
	}

	@SuppressWarnings("unchecked")
	/*private static DemographicInfoDTO getDemoInLocalLang() {
		String platformLanguageCode = "eng";
		String localLanguageCode = "ara";

		DemographicInfoDTO demographicInfoDTO = Builder.build(DemographicInfoDTO.class)
				.with(demographicInfo -> demographicInfo.setIdentity((IndividualIdentity)Builder.build(IndividualIdentity.class)
						.with(identity -> identity.setFullName((List<ValuesDTO>)Builder.build(LinkedList.class)
								.with(values -> values.add(Builder.build(ValuesDTO.class)
										.with(value -> value.setLanguage(platformLanguageCode))
										.with(value -> value.setValue("John Lawernce Jr")).get()))
								.with(values -> values.add(Builder.build(ValuesDTO.class)
										.with(value -> value.setLanguage(localLanguageCode))
										.with(value -> value.setValue("John Lawernce Jr")).get()))
								.get()))
						.with(identity -> identity.setDateOfBirth("2018/01/01")).with(identity -> identity.setAge(1))
						.with(identity -> identity.setGender((List<ValuesDTO>)Builder.build(LinkedList.class)
								.with(values -> values.add(Builder.build(ValuesDTO.class)
										.with(value -> value.setLanguage(platformLanguageCode))
										.with(value -> value.setValue("Male")).get()))
								.with(values -> values.add(Builder.build(ValuesDTO.class)
										.with(value -> value.setLanguage(localLanguageCode))
										.with(value -> value.setValue("Male")).get()))
								.get()))
						.with(identity -> identity.setAddressLine1((List<ValuesDTO>)Builder.build(LinkedList.class)
								.with(values -> values.add(Builder.build(ValuesDTO.class)
										.with(value -> value.setLanguage(platformLanguageCode))
										.with(value -> value.setValue("Address Line1")).get()))
								.with(values -> values.add(Builder.build(ValuesDTO.class)
										.with(value -> value.setLanguage(localLanguageCode))
										.with(value -> value.setValue("Address Line1")).get()))
								.get()))
						.with(identity -> identity.setAddressLine2((List<ValuesDTO>)Builder.build(LinkedList.class)
								.with(values -> values.add(Builder.build(ValuesDTO.class)
										.with(value -> value.setLanguage(platformLanguageCode))
										.with(value -> value.setValue("Address Line2")).get()))
								.with(values -> values.add(Builder.build(ValuesDTO.class)
										.with(value -> value.setLanguage(localLanguageCode))
										.with(value -> value.setValue("Address Line2")).get()))
								.get()))
						.with(identity -> identity.setAddressLine3((List<ValuesDTO>)Builder.build(LinkedList.class)
								.with(values -> values.add(Builder.build(ValuesDTO.class)
										.with(value -> value.setLanguage(platformLanguageCode))
										.with(value -> value.setValue("Address Line3")).get()))
								.with(values -> values.add(Builder.build(ValuesDTO.class)
										.with(value -> value.setLanguage(localLanguageCode))
										.with(value -> value.setValue("Address Line3")).get()))
								.get()))
						.with(identity -> identity.setRegion((List<ValuesDTO>)Builder.build(LinkedList.class)
								.with(values -> values.add(Builder.build(ValuesDTO.class)
										.with(value -> value.setLanguage(platformLanguageCode))
										.with(value -> value.setValue("Region")).get()))
								.with(values -> values.add(Builder.build(ValuesDTO.class)
										.with(value -> value.setLanguage(localLanguageCode))
										.with(value -> value.setValue("Region")).get()))
								.get()))
						.with(identity -> identity.setProvince((List<ValuesDTO>)Builder.build(LinkedList.class)
								.with(values -> values.add(Builder.build(ValuesDTO.class)
										.with(value -> value.setLanguage(platformLanguageCode))
										.with(value -> value.setValue("Province")).get()))
								.with(values -> values.add(Builder.build(ValuesDTO.class)
										.with(value -> value.setLanguage(localLanguageCode))
										.with(value -> value.setValue("Province")).get()))
								.get()))
						.with(identity -> identity.setCity((List<ValuesDTO>)Builder.build(LinkedList.class)
								.with(values -> values.add(Builder.build(ValuesDTO.class)
										.with(value -> value.setLanguage(platformLanguageCode))
										.with(value -> value.setValue("City")).get()))
								.with(values -> values.add(Builder.build(ValuesDTO.class)
										.with(value -> value.setLanguage(localLanguageCode))
										.with(value -> value.setValue("City")).get()))
								.get()))
						.with(identity -> identity.setResidenceStatus((List<ValuesDTO>)Builder.build(LinkedList.class)
								.with(values -> values.add(Builder.build(ValuesDTO.class)
										.with(value -> value.setLanguage(platformLanguageCode))
										.with(value -> value.setValue("National")).get()))
								.with(values -> values.add(Builder.build(ValuesDTO.class)
										.with(value -> value.setLanguage(localLanguageCode))
										.with(value -> value.setValue("National")).get()))
								.get()))
						.with(identity -> identity.setPostalCode("605110"))
						.with(identity -> identity.setPhone("8889992233"))
						.with(identity -> identity.setEmail("john.lawerence@gmail.com"))
						.with(identity -> identity.setReferenceIdentityNumber("123456789012"))
						.with(identity -> identity.setZone((List<ValuesDTO>)Builder.build(LinkedList.class)
								.with(values -> values.add(Builder.build(ValuesDTO.class)
										.with(value -> value.setLanguage(platformLanguageCode))
										.with(value -> value.setValue("Local Admin")).get()))
								.with(values -> values.add(Builder.build(ValuesDTO.class)
										.with(value -> value.setLanguage(localLanguageCode))
										.with(value -> value.setValue("Local Admin")).get()))
								.get()))
						.with(identity -> identity.setParentOrGuardianRID(new BigInteger("98989898898921913131")))
						.with(identity -> identity.setParentOrGuardianName((List<ValuesDTO>)Builder.build(LinkedList.class)
								.with(values -> values.add(Builder.build(ValuesDTO.class)
										.with(value -> value.setLanguage(platformLanguageCode))
										.with(value -> value.setValue("Parent/Guardian")).get()))
								.with(values -> values.add(Builder.build(ValuesDTO.class)
										.with(value -> value.setLanguage(localLanguageCode))
										.with(value -> value.setValue("Parent/Guardian")).get()))
								.get()))
						.with(identity -> identity.setIdSchemaVersion(1.0)).get()))
				.get();

		return demographicInfoDTO;
	}

	private static ApplicantDocumentDTO setApplicantDocumentDTO() throws RegBaseCheckedException {
		ApplicantDocumentDTO applicantDocumentDTO = new ApplicantDocumentDTO();
		
		//applicantDocumentDTO.setDocumentDetailsDTO(DataProvider.getDocumentDetailsDTO());
		*//*applicantDocumentDTO.setPhoto(DataProvider.getImageBytes("/applicantPhoto.jpg"));
		applicantDocumentDTO.setPhotographName("ApplicantPhoto.jpg");
		applicantDocumentDTO.setHasExceptionPhoto(true);
		applicantDocumentDTO.setExceptionPhoto(DataProvider.getImageBytes("/applicantPhoto.jpg"));
		applicantDocumentDTO.setExceptionPhotoName("ExceptionPhoto.jpg");
		applicantDocumentDTO.setQualityScore(89.0);
		applicantDocumentDTO.setNumRetry(1);*//*
		applicantDocumentDTO.setAcknowledgeReceipt(DataProvider.getImageBytes("/acknowledgementReceipt.jpg"));
		applicantDocumentDTO.setAcknowledgeReceiptName("RegistrationAcknowledgement.jpg");
		return applicantDocumentDTO;
	}*/

	private static Map<String, DocumentDto> getDocumentDetailsDTO() throws RegBaseCheckedException {
		Map<String, DocumentDto> documents = new HashMap<>();
		DocumentDto documentDetailsDTO = new DocumentDto();
		documentDetailsDTO.setDocument(DataProvider.getImageBytes("/proofOfAddress.jpg"));
		documentDetailsDTO.setType("Passport");
		documentDetailsDTO.setFormat("jpg");
		documentDetailsDTO.setValue("ProofOfIdentity");
		documentDetailsDTO.setOwner("Self");
		documents.put("proofOfIdentity", documentDetailsDTO);

		DocumentDto documentDetailsResidenceDTO = new DocumentDto();
		documentDetailsResidenceDTO.setDocument(DataProvider.getImageBytes("/proofOfAddress.jpg"));
		documentDetailsResidenceDTO.setType("Passport");
		documentDetailsResidenceDTO.setFormat("jpg");
		documentDetailsResidenceDTO.setValue("ProofOfAddress");
		documentDetailsResidenceDTO.setOwner("hof");
		documents.put("proofOfAddress", documentDetailsResidenceDTO);
		
		DocumentDto documentDetailsExceptionDTO = new DocumentDto();
		documentDetailsExceptionDTO.setDocument(DataProvider.getImageBytes("/proofOfAddress.jpg"));
		documentDetailsExceptionDTO.setType("COE");
		documentDetailsExceptionDTO.setFormat("jpg");
		documentDetailsExceptionDTO.setValue("ProofOfAddress");
		documentDetailsExceptionDTO.setOwner("hof");
		documents.put("proofOfException", documentDetailsResidenceDTO);
		
		return documents;
	}

	private static RegistrationMetaDataDTO getRegistrationMetaDataDTO() {

		RegistrationMetaDataDTO registrationMetaDataDTO = new RegistrationMetaDataDTO();
		//registrationMetaDataDTO.setRegistrationCategory("Parent");
		registrationMetaDataDTO.setGeoLatitudeLoc(13.0049);
		registrationMetaDataDTO.setGeoLongitudeLoc(80.24492);
		registrationMetaDataDTO.setCenterId("12245");
		registrationMetaDataDTO.setMachineId("yyeqy26356");
		//registrationMetaDataDTO.setRegistrationCategory("New");
		registrationMetaDataDTO.setApplicantTypeCode("007");
		registrationMetaDataDTO.setDeviceId("11143");
		registrationMetaDataDTO.setConsentOfApplicant("Yes");
		
		return registrationMetaDataDTO;
	}

	private static OSIDataDTO getOsiDataDTO() {
		OSIDataDTO osiDataDTO = new OSIDataDTO();
		osiDataDTO.setOperatorID("op0r0s12");
		osiDataDTO.setSupervisorID("s9ju2jhu");
		osiDataDTO.setOperatorAuthenticatedByPassword(true);
		osiDataDTO.setSuperviorAuthenticatedByPIN(true);
		return osiDataDTO;
	}

	private static List<AuditDto> getAuditDTOs() {
		LinkedList<AuditDto> auditDTOList = new LinkedList<>();

		addAuditDTOToList(auditDTOList, "Capture Demographic Data", "Data Capture", "Caputured demographic data");
		addAuditDTOToList(auditDTOList, "Capture Left Iris", "Iris Capture", "Caputured left iris");
		addAuditDTOToList(auditDTOList, "Capture Right Iris", "Iris Capture", "Caputured right iris");
		addAuditDTOToList(auditDTOList, "Capture Right Palm", "Palm Capture", "Caputured Right Palm");
		addAuditDTOToList(auditDTOList, "Capture Left Palm", "Palm Capture", "Caputured Left Palm");
		addAuditDTOToList(auditDTOList, "Capture Both Thumb", "Thumbs Capture", "Caputured Both Thumb");

		return auditDTOList;
	}

	private static void addAuditDTOToList(List<AuditDto> auditDTOList, String eventName, String eventType,
			String description) {
		LocalDateTime dateTime = LocalDateTime.now();

		AuditDto audit = new AuditDto();

//		audit.setUuid(String.valueOf(UUID.randomUUID().getMostSignificantBits()));
//		audit.setCreatedAt(dateTime);
//		audit.setEventId("1");
//		audit.setEventName(eventName);
//		audit.setEventType(eventType);
//		audit.setActionTimeStamp(dateTime);
//		audit.setHostName(RegistrationConstants.LOCALHOST);
//		audit.setHostIp(RegistrationConstants.LOCALHOST);
//		audit.setApplicationId("1");
//		audit.setApplicationName("Registration-UI");
//		audit.setSessionUserId("12345");
//		audit.setSessionUserName("Officer");
//		audit.setId("1");
//		audit.setIdType("registration");
//		audit.setCreatedBy("Officer");
//		audit.setModuleId("1");
//		audit.setModuleName("New Registration");
//		audit.setDescription(description);
		auditDTOList.add(audit);
	}

	public static List<UiFieldDTO> getFields() {
		List<UiFieldDTO> fields = new ArrayList<>();
		UiFieldDTO fullname = new UiFieldDTO();
		fullname.setId("fullName");
		fullname.setType("simpleType");
		HashMap<String, String> fullnameLabel = new HashMap<>();
		fullnameLabel.put("eng", "Full Name");
		fullnameLabel.put("ara", "Full Name");
		fullnameLabel.put("fra", "Full Name");
		fullname.setLabel(fullnameLabel);
		fields.add(fullname);

		UiFieldDTO region = new UiFieldDTO();
		region.setId("region");
		region.setType("simpleType");
		region.setLabel(fullnameLabel);
		fields.add(region);
		
		UiFieldDTO email = new UiFieldDTO();
		email.setId("email");
		email.setType("string");
		email.setLabel(fullnameLabel);
		fields.add(email);
		
		UiFieldDTO proofOfAddress = new UiFieldDTO();
		proofOfAddress.setId("proofOfAddress");
		proofOfAddress.setType("documentType");
		proofOfAddress.setLabel(fullnameLabel);
		fields.add(proofOfAddress);
		
		UiFieldDTO proofOfIdentity = new UiFieldDTO();
		proofOfIdentity.setId("proofOfIdentity");
		proofOfIdentity.setType("documentType");
		proofOfIdentity.setLabel(fullnameLabel);
		fields.add(proofOfIdentity);
		
		UiFieldDTO proofOfException = new UiFieldDTO();
		proofOfException.setId("proofOfException");
		proofOfException.setType("documentType");
		proofOfException.setSubType("POE");
		proofOfException.setLabel(fullnameLabel);
		fields.add(proofOfException);
		
		UiFieldDTO individualBiometrics = new UiFieldDTO();
		individualBiometrics.setId("applicant");
		individualBiometrics.setBioAttributes(getBioAttributes());
		individualBiometrics.setType("biometricsType");
		individualBiometrics.setLabel(fullnameLabel);
		fields.add(individualBiometrics);

		return fields;
	}
	
	private static List<String> getBioAttributes() {
		List<String> bioAttributes = new ArrayList<String>();
		bioAttributes.add(new String("leftEye"));
		bioAttributes.add(new String("rightEye"));
		bioAttributes.add(new String("rightLittle"));
		bioAttributes.add(new String("rightIndex"));
		bioAttributes.add(new String("rightRing"));
		bioAttributes.add(new String("rightMiddle"));
		bioAttributes.add(new String("leftIndex"));
		bioAttributes.add(new String("leftLittle"));
		return bioAttributes;
	}
}
