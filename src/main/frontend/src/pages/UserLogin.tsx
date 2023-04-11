import React, { useState, useEffect } from 'react';
import styled,{keyframes} from 'styled-components';
import TeamName from '../components/TeamName';
import Button from '../components/Button';
import {useNavigate} from 'react-router-dom';
import axios from 'axios';

const UserLogin = () => {
    const [inputId, setInputId] = useState('')
    const [inputPw, setInputPw] = useState('')
    // const [Loding, setLoding] = useState(false)
    const [passwordType,setPasswordType] = useState({
        type:'password',
        visible:false
    })
    const handlePasswordType = (e:any) => {
        setPasswordType(()=>{
            if(!passwordType.visible) {
                return {type: 'text', visible:true};
            }
            return {type:'password',visible:false};
        })
    }
    // input data 의 변화가 있을 때마다 value 값을 변경해서 useState 해준다
    const handleInputId = (e: { target: { value: React.SetStateAction<string>; }; }) => {
        setInputId(e.target.value)
    }

    const handleInputPw = (e: { target: { value: React.SetStateAction<string>; }; }) => {
        setInputPw(e.target.value)
    }

    // login 버튼 클릭 이벤트
    const onClickLogin = () => {
        navigate('/home')
    }
    const llogin = () => {
        axios.post("http://www.zena.co.kr/api/login", {
                email : inputId,
                password : inputPw
            })
            .then(() => navigate("/"))
            .catch()
    }
    
    let navigate = useNavigate();
    const [color1, setColor1] = useState('#1E00D3');
    const [color2, setColor2] = useState('#B7B7B7');

    const handleClick1 = () => {
        setColor1(color1 === '#B7B7B7' ? '#1E00D3' : '#1E00D3');
        setColor2(color2 === '#1E00D3' ? '#B7B7B7' : '#B7B7B7');
    };
    
    const handleClick2 = () => {
        setColor2(color2 === '#B7B7B7' ? '#1E00D3' : '#1E00D3');
        setColor1(color1 === '#1E00D3' ? '#B7B7B7' : '#B7B7B7');
    };
        
    return (
        <div>
            <_Wrapper>
            <_Form>
            {/* <_Logo src='YSIT22.png'></_Logo> */}
                <_Subtitle>안녕하세요!</_Subtitle>
                <_TeamName>
                    I Can Do <_TeamNameColor>IT콘텐츠과</_TeamNameColor>
                </_TeamName>
                <_BtnWrap>
                    <_Jobbtn type = 'button' style={{ color: color1 }} onClick={handleClick1}>학생</_Jobbtn>
                    <_JobLine/>
                    <_Jobbtn type = 'button' style={{ color: color2 }} onClick={handleClick2}>교사</_Jobbtn>
                </_BtnWrap>
            <_InputWrap>
            <_Label>아이디</_Label>
            <br />
            <_Input
            value={inputId}
            onChange={handleInputId}
            type="text"
            placeholder="이메일 아이디"
            />
        </_InputWrap>
        <_InputWrap>
            <_Label>비밀번호</_Label>
            <br />
            <_Input
            value={inputPw}
            onChange={handleInputPw}
            type={passwordType.type}
            placeholder="비밀번호 입력 (최소 8자)"
            minLength={8}
            />
            <_Logowrap onClick={handlePasswordType}>
                {passwordType.visible ? <_Logo src='eye1.svg'></_Logo> : <_Logo src='eye2.svg'></_Logo>}
            </_Logowrap>
        </_InputWrap>
            <Button onClick={llogin} backgroundColor={'#1E00D3'}>
                로그인 하기
            </Button>
            <_FindWrap>
                <_Find onClick={()=>navigate('/find-id')}>아이디 찾기</_Find>
                <_Line/>
                <_Find onClick={()=>navigate('/find-pw')}>비밀번호 찾기</_Find>
                <_Line/>
                <_Find onClick={()=>navigate('/sign-up')}>회원가입</_Find>
            </_FindWrap>
            </_Form>
            </_Wrapper>
        </div>
    );
};

export default UserLogin;

const _Logo = styled.img`
    width: 20px;
    height: 20px;
`;

const flow = keyframes`
0% {
    background-position: 0 0;
}
100% {
    background-position: 100% 0;
}
`;

const _Wrapper = styled.div`
    width: 100vw;
    height: 100vh;
    display: flex;
    align-items: center;
    justify-content: center;

    background: linear-gradient(to right bottom, #9786ff, #2805fc);
    animation: ${flow} 30s linear infinite;
`;

const _TeamName = styled.div`
font-size: 32px;
text-align: center;

margin-top: 10px;

font-weight: bold;
`;

const _TeamNameColor = styled.span`
color: #1e00d3;
`;

const _BtnWrap = styled.div`
padding: 5px 20px 5px 20px;
margin: 0px 50px 0px 50px;

display: flex;
`;

const _Form = styled.form`
    background-color: #ffffff;
    border-radius: 15px;

    display: flex;
    flex-direction: column;
    width: 500px;
    height: 530px;
    margin: 0 auto;

    box-shadow: 8px 8px 15px 5px rgba(0, 0, 0, 0.25);

    > label {
    :first-of-type {
        margin-top: 20px;
    }
        margin-top: 20px;
    }
    > button {
        margin-top: 26px;
    }
`;

const _Jobbtn = styled.button`
    padding: 5px 20px 5px 20px;
    margin: 0px 50px 0px 50px;
    width: 180px;
    height: 47px;

    color: #b7b7b7;

    font-size: 20px;
    font-weight: bold;
    font-family: sans-serif;

    background: none;
    border: none;
    cursor: pointer;
`;

const _JobLine = styled.span`
    width: 3px;
    height: 20px;

    margin-top: 12px;

    background-color: gray;
`

const _Line = styled.span`
    width: 1px;
    height: 15px;

    margin-top: 15px;

    background-color: rgb(174, 174, 174);
`;

const _Subtitle = styled.div`
    font-family: 'Noto Sans KR';
    font-size: 25px;
    text-align: center;
    margin: 0;
    margin-top: 20px;

    font-weight: bold;
`

const _FindWrap = styled.div`
    display: flex;
    justify-content: space-around;
    //margin: 0 auto;
    
    margin-top: 30px;
    margin-left: 35px;
    margin-right: 35px;
`;

const _Find = styled.button`
    width: 100px;
    height: 47px;
    background: none;
    color: #666666;
    border: none;
    cursor: pointer;
    font-family: 'Noto Sans';
    font-style: normal;
    /* font-weight: 600; */
    font-size: 15px;
    font-weight: bold;
    /* margin-left: 10px;
    margin-right: 10px; */
`;

const _Label = styled.label`
font-size: 13px;

margin-left: 5px;
font-weight: bold;
`;

const _Input = styled.input`
width: 365px;
height: 50px;
margin-top: 3px;
font-weight: bold;
border: 1px solid #e5e5e5;
:focus {
    border: 1.8px solid blue;
}
border-radius: 12px;
padding-left: 10px;

border-color: gray;
outline: none;
`;

const _PWInput = styled.input`
width: 365px;
height: 50px;
margin-top: 3px;
font-weight: bold;
border: 1px solid #e5e5e5;
:focus {
    border: 1.8px solid blue;
}
border-radius: 12px;
padding-left: 10px;

border-color: gray;
outline: none;
`;

const _InputWrap = styled.div`
margin: 0 auto;
margin-top: 10px;
`;

const _Logowrap = styled.div`
    display: flex;
    justify-content: end;
    position: relative;
    z-index: 1;
    bottom: 35px;
    margin-right: 10px;
    width: 10px;
    margin-left: 95%;
`;

