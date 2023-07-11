package com.kakao.saramaracommunity.member.service;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.kakao.saramaracommunity.member.dto.ErrorCode;
import com.kakao.saramaracommunity.member.dto.MemberResDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Log4j2
@RequiredArgsConstructor
@Service
public class MemberServiceMethod {

	// 회원가입 - Email 중복확인, 로그인 - 잘못된 Email
	public boolean isDuplicatedEmail(long emailCount){
		try{
			if(emailCount == 1l){
				return true;
			} else {
				return false;
			}
		} catch (Exception e){
			log.error(e.getMessage());
			return true;
		}
	}

	// NickName 중복 확인
	public boolean isDuplicatedNickname(long nicknameCount){
		try{
			if(nicknameCount == 1l){
				return true;
			} else {
				return false;
			}
		} catch (Exception e){
			log.error(e.getMessage());
			return true;
		}
	}

	// NickName 수정 시 중복 확인
	public boolean isChangeNickNameDuplicated(String currentNickname, String changeNickname, boolean existNickname){
		boolean result = false;

		// 현재 닉네임과 변경할 닉네임이 일치하지 않고
		if(!currentNickname.equals(changeNickname)){
			// 변경할 닉네임이 중복된다면 닉네임 중복으로 변경 불가
			if(existNickname){
				result = true;
			}
		}
		return result;
	}

	// Password 변경 시 사용자가 입력한 현재 비밀번호와 저장된 현재 비밀번호 일치 여부
	private final PasswordEncoder encoder;
	public boolean checkCurrentPw(String inputCurrentPw, String storedCurrentPw){
		boolean result = true;

		if(!encoder.matches(inputCurrentPw, storedCurrentPw)){
			result = false;
		}
		return result;
	}

	// Password 변경 시 사용자가 입력한 변경할 비밀번호와 그 비밀번호를 확인하기 위해 다시 입력한 비밀번호가 일치하는지 여부
	public boolean checkChangedPw(String changePW, String changePWCheck){
		boolean result = true;

		if(!changePW.equals(changePWCheck)){
			result = false;
		}
		return result;
	}

	// HttpStatus.OK가 아닌 경우의 응답에 대해서 대응하는 ErrorCode의 HttpStatus로 변경하는 메서드
	public HttpStatus changeStatus(MemberResDto memberResDto) {
		try {
			if (!memberResDto.isSuccess()){
				return memberResDto.getErrorCode().getHttpStatus();
			}
			return HttpStatus.OK;
		} catch (Exception e){
			log.warn(e.getMessage());
			return HttpStatus.OK;
		}
	}

	// Try-Catch 중 Catch 절에서 500 에러에 대한 결과를 응답하는 메서드
	public MemberResDto internalServerErrorResult(Exception e){
		log.error(e.getMessage());
		log.error(e.getStackTrace());

		MemberResDto internalServerResult = MemberResDto.builder()
			.success(false)
			.errorCode(ErrorCode.INTERNAL_SERVER_ERROR)
			.build();

		return internalServerResult;
	}
}
