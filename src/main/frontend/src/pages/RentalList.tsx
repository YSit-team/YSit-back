import React, { useState, useEffect } from 'react';
import styled from 'styled-components';
import Menubar from '../components/Menubar'
import axios from "axios";
import { useNavigate, Link } from "react-router-dom";


const RentalList = () => {
    let navigate = useNavigate();
    let [apply, setapply] = useState(null);
    const [rentaldata, setrentaldata] = useState([]);

    useEffect(()=>{
        axios.post("http://localhost:8080/api/rental/rentalHistory").then((data)=>{
            setrentaldata(data.data);
        })
    },[])
    console.log(rentaldata);
    // useEffect(() => {
    //     const userget = (response: any) =>{
    //         axios
    //         .get("http://www.zena.co.kr/api/register")
    //         .then((respone) => (setapply(response.data)))
    //         .catch((error) => {console.error(error)});
    //         console.log(response);
    //     }
    // });

    return (
    <>
        <Menubar/>
        <Bar>신청내역</Bar>
        <_Graybar>
            <div>이름</div>
            <div>일시</div>
        </_Graybar>
        {
            rentaldata.map((item: any) => (
            <_Link to={`/listdetail/${item.id}`}>
                <Listwrap>
                <_List>{item.uploader}</_List>
                <_List>날짜</_List>
                </Listwrap>
            </_Link>
            ))
        }

        
    </>
    );
};

export default RentalList;

const Bar = styled.div`
    width: 100vw;
    height: 50px;
    margin-top: 69px;
    display: flex;
    justify-content: center;
    align-items: center;
    font-size: 20px;
    font-weight: bold;
    border-bottom: 0.8px solid #999999;
`
const _Graybar = styled.div`
    background: #b5b5b5;
    width: 100vw;
    height: 30px;
    border-top: 1.5px solid gray;
    display: flex;
    justify-content: space-between;
    align-items: center;
    >div{
        font-weight: 600;
        width: 130px;
        text-align: center;
    }
`

const Listwrap = styled.ul`
    display: flex;
    justify-content: space-between;
    padding-top: 15px;
    padding-bottom: 15px;
    border-bottom: 0.8px solid #999999;
    cursor: pointer;
`

const _List = styled.li`
    font-weight: 400;
    width: 130px;
    text-align: center;
`

const _Link = styled(Link)`
    text-decoration: none;
    color: inherit;
`