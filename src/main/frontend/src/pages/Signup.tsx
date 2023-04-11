import styled from "styled-components";
import React, { useState, useEffect } from "react";
import axios from "axios";
import { useNavigate } from "react-router-dom";

const Signup = () => {
  const [id, setId] = useState("");
  const [pw, setPw] = useState("");
  const [number, setNumber] = useState("");
  const [name, setName] = useState("");
  const [form, setform]: any = useState("");
  const [job, setJob] = useState("");
  const [tel, setTel] = useState("");
  const [isInputVisible, setIsInputVisible] = useState(true);
  const [inputValue, setInputValue] = useState("");
  let navigate = useNavigate();
  const [color1, setJobColor1] = useState("#1E00D3");
  const [color2, setJobColor2] = useState("#B7B7B7");

  const handleJobClick1 = () => {
    setJobColor1(color1 === "#B7B7B7" ? "#1E00D3" : "#1E00D3");
    setJobColor2(color2 === "#1E00D3" ? "#B7B7B7" : "#B7B7B7");
    setIsInputVisible(true);
  };

  const handleJobClick2 = () => {
    setJobColor2(color2 === "#B7B7B7" ? "#1E00D3" : "#1E00D3");
    setJobColor1(color1 === "#1E00D3" ? "#B7B7B7" : "#B7B7B7");
    setIsInputVisible(false);
  };

  // const res = await axios.post('http://www.zena.co.kr/api/register', {
  //         email: email,
  //         password: password,
  //         tel: tel,
  //         studentID: studentID,
  //         name: name})

  const signUp = () => {
    //axios.post("http://3.38.26.161:8080/api/user/login"
    axios.post("http://www.zena.co.kr/api/register", {
        job: job, //학생, 교사
        email: id, //이메일아이디
        password: pw, //비밀번호
        tel: tel, //전화번호
        studentID: number, //학번
        name: name, //이름
      })
      .then(() => navigate("/"))
      .catch();
    console.log(form);
  };

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

  useEffect(() => {
    if (tel.length === 10) {
      setTel(tel.replace(/(\d{3})(\d{3})(\d{4})/, '$1-$2-$3'));
    }
    if (tel.length === 13) {
      setTel(tel.replace(/-/g, '').replace(/(\d{3})(\d{4})(\d{4})/, '$1-$2-$3'));
    }
  }, [tel]);

  return (
    <_Wrap >
      <form
        onSubmit={(event: any) => {
          (job === '' ? setJob("student") : setJob(job));
          event.preventDefault();
          setform({
            job,
            id,
            pw,
            tel,
            number,
            name,
          });
        }}
      >
        <_FormWrap isInputVisible={isInputVisible}>
        <_Subtitle>환영합니다!</_Subtitle>
          <_TeamName>
            I Can Do <_TeamNameColor>IT콘텐츠과</_TeamNameColor>
          </_TeamName>
          <_BottonWrap>
            <_JobBtn
              style={{ color: color1 }}
              className="stduent"
              onClick={() => {
                setJob("student");
                console.log(job);
                // isInputVisible==true;
                handleJobClick1();
                }}>
              학생
            </_JobBtn>
            <_Line></_Line>
            <_JobBtn style={{ color: color2 }}
              onClick={() => {
                setJob("teacher");
                console.log(job);
                
                // isInputVisible==true;
                handleJobClick2();
              }}>
              교사
            </_JobBtn>
          </_BottonWrap>
          <_InputWrap>
            <_Label>아이디</_Label>
            <br />
            <_Input
              value={id}
              onChange={(event) => {setId(event.target.value);
                console.log(id);
              }}
              type="text"
              placeholder="이메일 아이디"
            />
          </_InputWrap>
          <_InputWrap>
            <_Label>비밀번호</_Label>
            <br />
            <_Input
              value={pw}
              onChange={(event) => {setPw(event.target.value);
                console.log(pw);
              }}
              type={passwordType.type}
              placeholder="비밀번호 입력 (최소 8자)"
              minLength={8}
              maxLength={12}
            />
          </_InputWrap>
          <_Logowrap onClick={handlePasswordType}>
              {passwordType.visible ? <_Logo src='eye1.svg'></_Logo> : <_Logo src='eye2.svg'></_Logo>}
          </_Logowrap>
          <_InputWrap>
            <_Label>전화번호</_Label>
            <br />
            <_Input
              value={tel}
              onChange={(event) => {setTel(event.target.value);
                console.log(tel);
                const regex = /^[0-9\b -]{0,13}$/;
                if (regex.test(event.target.value)) {
                  setTel(event.target.value);
                }
              }}
              type="text"
              id="phoneNum" 
              minLength={11}
              maxLength={13}
              placeholder="'-'없이 입력하세요."
            />
          </_InputWrap>
          {isInputVisible && (
          <_InputWrap>
            <_Label>학번</_Label>
            <br />
            <_Input
              value={number}
              onChange={(event) => {setNumber(event.target.value);
                console.log(number);
              }}
              type="text"
              placeholder="예. 3216"
              minLength={4}
              maxLength={4}
            />
          </_InputWrap>
          )}
          <_InputWrap>
            <_Label>이름</_Label>
            <br />
            <_Input
              value={name}
              onChange={(event) => {setName(event.target.value);
                console.log(name);
              }}
              type="text"
              placeholder="이름을 입력해 주세요."
              minLength={2}
              maxLength={5}
            />
          </_InputWrap>
          <_SignUpBtnWrap>
            <_SignUpBtn type="submit" onClick={signUp}>
              가입하기
            </_SignUpBtn>
          </_SignUpBtnWrap>
        </_FormWrap>
      </form>
    </_Wrap>
  );
};

export default Signup;

const _Wrap = styled.div`
  background: linear-gradient(to right bottom, #9786ff, #2805fc);
  width: 100vw;
  height: 100vh;
  display: flex;
  justify-content: center;
  align-items: center;
  
  @media (max-width: 600px) {
    background: none;
  }
`;

interface ContainerProps {
  isInputVisible: any;
}

const _FormWrap = styled.div<ContainerProps>`
  display: flex;
  flex-direction: column;

  background-color: #ffffff;
  height: ${({ isInputVisible }) => (isInputVisible ? '710px' : '620px')};
  /* height: (job === "student" ? '690px' : '610px');
  
  if (job === student) {
    height: 690px;
  } else {
    height: 610px;
  } */

  width : 500px;

  box-shadow: 8px 8px 15px 5px rgba(0, 0, 0, 0.25);
  border-radius: 15px;

  @media (max-width: 600px) {
    box-shadow: none;
  }
`;

const _Subtitle = styled.div`
    font-family: 'Noto Sans KR';
    font-size: 25px;
    text-align: center;
    margin: 0;
    margin-top: 20px;
    font-weight: bold;
`

const _TeamName = styled.div`
  font-size: 32px;
  text-align: center;

  margin-top: 10px;

  font-weight: bold;
`;

const _TeamNameColor = styled.span`
  color: #1e00d3;
`;

const _BottonWrap = styled.div`
  padding: 5px 20px 5px 20px;
  margin: 0px 50px 0px 50px;

  display: flex;
`;

const _JobBtn = styled.button`
  padding: 5px 20px 5px 20px;
  margin: 0px 50px 0px 50px;

  color: #b7b7b7;

  font-size: 20px;
  font-weight: bold;
  font-family: sans-serif;

  background: none;
  border: none;
  cursor: pointer;
`;

const _Line = styled.span`
  width: 1px;
  height: 20px;

  margin-top: 6px;

  background-color: gray;
`;

const _Label = styled.label`
  font-size: 13px;

  margin-left: 5px;
  font-weight: bold;
`;

const _Input = styled.input`
  width: 400px;
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

const _SignUpBtn = styled.button`
  width: 412px;
  height: 60px;
  font-size: 20px;

  background: #1e00d3;
  border: 0px solid #e5e5e5;
  border-radius: 12px;
  font-weight: bold;

  color: white;
  cursor: pointer;
`;

const _SignUpBtnWrap = styled.div`
  margin-top: 20px;
  
  display: flex;
  justify-content: center;
`;

const _Logo = styled.img`
    width: 20px;
    height: 20px;
`;

const _Logowrap = styled.div`
    display: flex;
    justify-content: end;
    position: relative;
    z-index: 1;
    bottom: 35px;
    margin-right: 10px;
    width: 10px;
    margin-left: 87%;
    margin-bottom: -19px;
`;