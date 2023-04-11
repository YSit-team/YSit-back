import React, {useEffect, useState} from 'react';
import styled from 'styled-components';
import axios from 'axios';
import Menubar from '../components/Menubar'
import { useParams} from 'react-router-dom';

const ListDetail = () => {
    const { id } = useParams(); // get the product ID from the URL
    const [product, setProduct]:any = useState([]);
    const [Accept, setAccept] = useState(true)

    useEffect(()=>{
        axios.get("/rental_list.json").then((response) => {
            const foundProduct = response.data.find(
            (product: any) => product.id == parseInt(id || '', 10)//////데이터가 안불러와짐
            );
            if (foundProduct) {
            setProduct(foundProduct);
            }
            console.log(product)
        })
        .catch((error) => console.log(error));
    },[id]);

    if (!product) {
        return <div>Product not found.</div>;
    }

    
    return (
    <>
    <_Wrap>
    <Menubar/>
    <_Header>{product.number} {product.name}님의 신청</_Header>
    <_List>기자재 목록</_List>
    <_Write>신청서</_Write>
    <_Writewrap>
        <_Inputtitle>이름 : {product.name}</_Inputtitle>
        <_Inputtitle>학번 : {product.number}</_Inputtitle>
        <_Inputtitle>이용인원 : {product.person}</_Inputtitle>
        <_Inputtitle>대여기간 : {product.period}</_Inputtitle>
        <_Inputtitle>불출시점 : {product.meet}</_Inputtitle>
        <_Inputtitle>전화번호 : {product.phonenum}</_Inputtitle>
        <_Inputtitle>이용목적 : {product.purpose}</_Inputtitle>
    </_Writewrap>
    <Btnwrap>
        <_SubmitBtn bgcolor="#01d705" color="#ffffff" 
            onClick={()=>{
                setAccept(true)
                alert("수락되었습니다.")
                console.log(Accept)
                axios.post('https://jsonplaceholder.typicode.com/posts', {Accept})
                .then(response => {
                    console.log(response.data);
                })
                .catch(error => {
                    console.error(error);
                });
            }}>수락</_SubmitBtn>
        <_SubmitBtn bgcolor="#f02a2b" color="#ffffff"
            onClick={()=>{
                setAccept(false)
                alert("거절되었습니다.")
                console.log(Accept)
                axios.post('https://jsonplaceholder.typicode.com/posts', {Accept})
                .then(response => {
                    console.log(response.data);
                })
                .catch(error => {
                    console.error(error);
                });
            }}>거절</_SubmitBtn>
    </Btnwrap>
    </_Wrap>
    </>
    );
};

export default ListDetail;

const _Wrap = styled.div`
    margin-top: 100px;
    display: grid;
    grid-template-columns: 1fr 3fr 1fr;
    grid-template-areas:
        "header header header"
        ". listtitle ."
        ". list ."
        ". writetitle ."
        ". write ."
        ". btn .";
`

const _Header = styled.header`
    width: 100%;
    margin-top: 30px;
    font-size: 28px;
    font-weight: 750;
    color: #000000;
    text-align: center;
    grid-area: header;
`

const _List = styled.div`
    width: 700px;
    font-size: 23px;
    font-weight: 700;
    color: #000000;
    box-shadow: inset 0 -2px 0 #1E00D3;
    padding-bottom: 10px;
    margin-top: 10px;
    grid-area: listtitle;
`

const _Write = styled.div`
    width: 700px;
    font-size: 23px;
    font-weight: 700;
    color: #000000;
    box-shadow: inset 0 -2px 0 #1E00D3;
    padding-bottom: 10px;
    margin-top: 10px;
    grid-area: writetitle;
`

//제출버튼부모
const Btnwrap = styled.div`
    display: flex;
    justify-content: center;
    grid-area: btn;
`

//제출버튼
interface Container{
    bgcolor: any;
    color: any;
}
const _SubmitBtn = styled.button<Container>`
    width: 25vw;
    height: 60px;
    border: none;
    color : ${props => props.color};
    border-radius: 15px;
    background: ${props => props.bgcolor};
    margin: 5px;
    margin-top: 15px;
    font-size: 16px;
    font-weight: bold;
    cursor: pointer;
    @media (max-width: 600px) {
    font-size: 13px;
    }
`

const _Writewrap = styled.div`
    width: 100%;
    display: flex;
    flex-direction: column;
    grid-area: write;
`

const _Inputtitle = styled.div`
    font-size: 20px;
    font-weight: 500;
    padding-top: 20px;
    padding-bottom: 20px;
    /* padding-left: 10px; */
    text-align: center;
    /* box-shadow: inset 0 -1px 0 #888888; */
    display: flex;
    justify-content: center;
`


