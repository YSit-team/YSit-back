import React, { useState } from 'react';
import Menubar from '../components/Menubar'
import styled from 'styled-components';
import axios from "axios";

const AddCamera = () => {
    const [Image, setImage] = useState();
    const [name, setname] = useState("");
    const [sort, setsort] = useState("");
    const [form, setform]: any = useState("");
    const handleImageUpload = (event:any) => {
        setImage(event.target.files[0]);
        console.log(Image)
    };
    const handleSubmit = (event: { preventDefault: () => void; }) => {
        event.preventDefault();
        setform({
            name,
            Image,
            sort
        });
        axios.post("http://www.zena.co.kr/api/register", {
            name: name, //이름
            url: Image,
            sort: sort
        })
        .then(() => (alert("전송되었습니다!")))
        .catch();
        console.log(form);
        // handle form submission logic here
    };
    return (
    <>
    <Menubar/>
    <FormWrapper>
    <h1>기자재 추가하기</h1>
    <form onSubmit={handleSubmit}>
        <InputWrapper>
        <Label htmlFor="name">장비명</Label>
        <Input type="text" id="name" name="name" placeholder="기자재 이름을 입력해주세요" 
            onChange={(event) => {
            setname(event.target.value);
                    console.log(name);
                    }}required />
        </InputWrapper>
        <InputWrapper>
        <Label htmlFor="resume">이미지</Label>
        <Input type="file" id="resume" name="resume" required onChange={handleImageUpload} />
    {/* {image && <img src={URL.createObjectURL(image)} alt="Selected Image" />} 이미지를 보여주는 코드 */}
        </InputWrapper>
        <InputWrapper>
        <Label htmlFor="분류">분류</Label>
        <Select id="sort" name="position" onChange={(event) => {
            setsort(event.target.value);
                    console.log(sort);
                    }}required>
            <Option value="" disabled selected>장비의 종류를 선택해주세요</Option>
            <Option value="Camera">카메라</Option>
            <Option value="Tripod">삼각대</Option>
            <Option value="Lighting">조명</Option>
            <Option value="Recorder">녹음</Option>
        </Select>
        </InputWrapper>
        <Button type="submit">전송</Button>
    </form>
    </FormWrapper>
    </>
    );
};

export default AddCamera;

const FormWrapper = styled.div`
display: flex;
flex-direction: column;
align-items: center;
margin-top: 70px;
`;

const InputWrapper = styled.div`
display: flex;
flex-direction: column;
margin-bottom: 1rem;
`;

const Label = styled.label`
font-size: 1.2rem;
margin-bottom: 0.5rem;
`;

const Input = styled.input`
padding: 0.5rem;
font-size: 1.2rem;
border: 1px solid #ccc;
border-radius: 4px;
`;

const Button = styled.button`
padding: 0.5rem 1rem;
font-size: 1.2rem;
border: none;
border-radius: 4px;
background-color: #0077cc;
color: #fff;
margin-top: 5px;
cursor: pointer;

&:hover {
    background-color: #005ea8;
}
`;

const Option = styled.option`
font-size: 1.2rem;
`;

const Select = styled.select`
padding: 0.5rem;
font-size: 1.2rem;
border: 1px solid #ccc;
border-radius: 4px;
`;
