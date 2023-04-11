import React, {useState, useEffect} from 'react';
import styled from 'styled-components';
import Menubar from '../components/Menubar'
import Productlist from '../Productlist.json'
import { useNavigate } from "react-router-dom";
import axios from "axios";

const Rental = () => {
    const [studentID, setstudentID] = useState("");
    const [name, setName] = useState("");
    const [form, setform]: any = useState("");
    const [person, setperson] = useState("");
    const [period1, setperiod1] = useState("");
    const [period2, setperiod2] = useState("");
    const [meet1, setmeet1] = useState("");
    const [meet2, setmeet2] = useState("");
    const [phonenum, setphonenum] = useState("");
    const [purpose, setpurpose] = useState("");
    const [cart, setcart]: any[] = useState([]);
    const [isModalVisible, setIsModalVisible] = useState(false);
    let [Product, setProduct] = useState(Productlist);
    const [border1, setborder1] = useState('1px solid #B7B7B7');
    let navigate = useNavigate();

    useEffect(() => {
        if (phonenum.length === 10) {
        setphonenum(phonenum.replace(/(\d{3})(\d{3})(\d{4})/, '$1-$2-$3'));
        }
        if (phonenum.length === 13) {
        setphonenum(phonenum.replace(/-/g, '').replace(/(\d{3})(\d{4})(\d{4})/, '$1-$2-$3'));
        }
    }, [phonenum]);

    const _Submit = () => {
        alert("전송되었습니다")
        //axios.post("http://3.38.26.161:8080/api/user/login"
        axios.post("http://localhost:8080/api/rental/rentals", {
            uploader: name, //이름
            // studentID: studentID, //학번
            headCount: person, //이용인원
            rentalDate: period1,
            returnDate: period2,
            // period: (period1+"~"+period2), //대여기간
            // meet: (meet1+"~"+meet2), //불출시점
            reason: purpose //이용목적
            // "name" : "정채윤",
            // "number" : "2218",
            // "person" : "5",
            // "period" : "3/14 ~ 3/20",
            // "meet" : "7:00pm ~ 10:00am",
            // "phonenum" : "01050610163",
            // "purpose" : "응애"
        })
        .then(() => (alert("전송되었습니다!")))
        .catch();
        console.log(form);
    };

    return (
        <>
        <Menubar/>
        <_Wrap>
            <_Header>신청하기</_Header>
            <_Menulist>
                {/* <_listtitle>장비대여
                    <_listitem>신청목록</_listitem>
                    <_listitem>나의 신청내역</_listitem>
                    <_listitem>문의하기</_listitem>
                </_listtitle> */}
            </_Menulist>
            <_Listwrap>
                <_Subtext>기자재 목록<_Addbtn onClick={() => setIsModalVisible(!isModalVisible)}>추가하기 +</_Addbtn><_Addbtn2 onClick={() => navigate("/add-camera")}>기자재 추가하기</_Addbtn2>
                <Cartwrap>
                            {
                                cart.map((a: any, i: string | number)=>{
                                        return (
                                            <Cartproductwrap key={i}>
                                            <CartBorder style={{ border: border1 }}>
                                            <CartImg src={`product/${cart[i].url}`} />
                                            </CartBorder>
                                            <CartName>{cart[i].name}</CartName>
                                            </Cartproductwrap>
                                        )
                                        })
                                }
                            </Cartwrap>
                    {isModalVisible && (
                    <ModalWrapper>
                        <ModalContent>
                            <_Headmodal>
                                기자재 추가하기
                            </_Headmodal>
                            <Cartwrap>
                            {
                                cart.map((a: any, i: string | number)=>{
                                        return (
                                            <Cartproductwrap key={i}>
                                            <CartBorder style={{ border: border1 }}>
                                            <CartImg src={`product/${cart[i].url}`} />
                                            </CartBorder>
                                            <CartName>{cart[i].name}</CartName>
                                            </Cartproductwrap>
                                        )
                                        })
                                }
                            </Cartwrap>
                            <RentalListWrap>
                                <Sortmenu><_Sort>카메라</_Sort><_Sort>삼각대</_Sort><_Sort>조명</_Sort><_Sort>녹음</_Sort><Search/></Sortmenu>
                                {
                                Product.map((a, i)=>{
                                        return (
                                            
                                            <Productwrap onClick={()=>{
                                                const cartItem = {
                                                    id : Product[i].id,
                                                    name : Product[i].name,
                                                    url : Product[i].url,
                                                    sort : Product[i].sort
                                                }
                                                setcart([...cart, cartItem]);
                                            }} key={i}>
                                            <ImgBorder style={{ border: border1 }}>
                                            <ProductImg src={`product/${Product[i].url}`} />
                                            </ImgBorder>
                                            <ProductName>{Product[i].name}</ProductName>
                                            </Productwrap>
                                        )
                                        })
                                }
                            </RentalListWrap>
                            {/* 확인버튼은 저장되게 닫기버튼은 취소로 */}
                            <OKbtn onClick={() => setIsModalVisible(!isModalVisible)}>확인</OKbtn><Closebtn onClick={() => setIsModalVisible(!isModalVisible)}>닫기</Closebtn>
                        </ModalContent>
                    </ModalWrapper>
                )}
            </_Subtext>
            </_Listwrap>
            <_Writewrap 
            onSubmit={(event: any) => {
            event.preventDefault();
            setform({
                cart,
                name,
                studentID,
                person,
                period1,
                period2,
                meet1,
                meet2,
                phonenum,
                purpose
            });
            }}>
                <_Subtext>신청서 작성</_Subtext>
                <_Inputtitle>이름<_Input onChange={(event) => {
                    setName(event.target.value);
                    console.log(name);
                    }}
                    required
                    type="text"
                    placeholder='이름을 입력해주세요.' width={"150px"}/></_Inputtitle>
                <_Inputtitle>학번<_Input 
                onChange={(event) => {
                    setstudentID(event.target.value);
                    console.log(studentID);
                    }}
                    type="text"
                    placeholder='학번을 입력해주세요.' width={"150px"}/></_Inputtitle>
                <_Inputtitle>이용인원<_Input 
                onChange={(event) => {setperson(event.target.value);
                    console.log(person);
                    }}
                    required
                    type="number"
                placeholder='9' width={"35px"} />명</_Inputtitle>
                <Rentaldate>
                <_Inputtitle>대여기간
                    <_Dropdownwrap>
                    <input onChange={(event) => {
                        setperiod1(event.target.value);
                        console.log(period1);
                        }}
                        required
                        type="date"/> 
                        <_Line1/>
                    <input onChange={(event) => {
                        setperiod2(event.target.value);
                        console.log(period2);
                        }}
                        required
                        type="date"/>
                    </_Dropdownwrap>
                </_Inputtitle>
                </Rentaldate>
                <Rentaldate>
                <_Inputtitle>불출시점
                    <_Dropdownwrap>
                        <input onChange={(event) => {
                            setmeet1(event.target.value);
                            console.log(meet1);
                            }}
                            required
                            type="time"/>
                        <_Line1/>
                        <input onChange={(event) => {
                            setmeet2(event.target.value);
                            console.log(meet2);
                            }}
                            required
                            type="time"/>
                    </_Dropdownwrap>
                </_Inputtitle>
                </Rentaldate>
                <_Inputtitle>연락처<_Input 
                    value={phonenum}
                    onChange={(event) => {setphonenum(event.target.value);
                        console.log(phonenum);

                        const regex = /^[0-9\b -]{0,13}$/;
                        if (regex.test(event.target.value)) {
                        setphonenum(event.target.value);
                        }
                    }} 
                    required type="tel" placeholder="01012345678" maxLength={13}></_Input></_Inputtitle>
                <_Input2title>이용목적 및 이용내역
                    <_Input2wrap>
                        <_Subinputtitle>※사용 용도, 작품 내용 등을 구체적으로 적어주세요.</_Subinputtitle>
                        <_Input2 
                        onChange={(event) => {
                            setpurpose(event.target.value);
                            console.log(purpose);
                            }}
                            required
                        placeholder='- 이용목적: (예: 영화콘텐츠 제작, 미디어교육, 행사)&#13;&#10;
                            - 촬영일자:  월  일&#13;&#10;
                            - 사용장소:&#13;&#10;
                            - 제작내용:'>
                        </_Input2>
                    </_Input2wrap>
                </_Input2title>
                            <Btnwrap><_SubmitBtn type="submit" onClick={_Submit} bg="#1E00D3" color="#ffffff">제출하기</_SubmitBtn><_SubmitBtn bg="#ffffff" color="#000000">취소</_SubmitBtn></Btnwrap>
            </_Writewrap>
            
        </_Wrap>
        </>
    );
};

export default Rental;

const _Wrap = styled.div`
    margin-top: 70px;
    display: grid;
    grid-template-columns: 1fr 3fr 1fr;
    grid-template-areas:
        "header header header"
        "sbmenu list ."
        ". write ."
        ". btn .";
`

//제목
const _Header = styled.header`
    width: 100%;
    margin-top: 30px;
    font-size: 28px;
    font-weight: 750;
    color: #000000;
    text-align: center;
    grid-area: header;
`

//기자재목록
const _Listwrap = styled.div`
    width: 100%;
    grid-area: list;
    margin-top: 10px;
    display: flex;
    justify-content: space-between;
`

//기자재목록텍스트
const _Subtext = styled.div`
    width: 100%;
    font-size: 23px;
    font-weight: 700;
    color: #000000;
    box-shadow: inset 0 -2px 0 #1E00D3;
    padding-bottom: 10px;
`

const _Addbtn2 = styled.button`
    width: 150px;
    height: 30px;
    border: none;
    border-radius: 5px;
    background-color: #1E00D3;
    float: right;
    color: white;
    font-size: 14px;
    font-weight: 900;
    margin-right: 5px;
    cursor: pointer;
`
//추가하기
const _Addbtn = styled.button`
    width: 100px;
    height: 30px;
    border: none;
    border-radius: 5px;
    background-color: #1E00D3;
    color: white;
    float: right;
    font-size: 14px;
    font-weight: 900;
    cursor: pointer;
`

const _Writewrap = styled.form`
    width: 100%;
    margin-top: 10px;
    display: flex;
    flex-direction: column;
    grid-area: write;
    
`

const _Inputtitle = styled.div`
    margin-top: 20px;
    font-size: 20px;
    font-weight: 500;
    padding-bottom: 20px;
    padding-left: 10px;
    box-shadow: inset 0 -1px 0 #888888;
    display: flex;
`

const _Input = styled.input`
    width:${props => props.width};
    height: 20px;
    margin-left: 20px;
    border: 1px solid #888888;
    border-radius: 3px;
    padding-left: 5px;
`

//드롭다운 부모
const _Dropdownwrap = styled.div`
    margin-left: 20px;
    display: flex;
    align-items: center;
`



const _Line1 = styled.div`
    width: 8px;
    height: 1.3px;
    background-color: #000000;
    margin:0 10px 0 10px;
`

//이용목적 및 이용내역 부모
const _Input2wrap = styled.div`
    display: flex;
    flex-direction: column;
`

//텍스트
const _Input2title = styled.div`
    margin-top: 20px;
    font-size: 20px;
    font-weight: 500;
    padding-bottom: 20px;
    padding-left: 10px;
    box-shadow: inset 0 -1px 0 #888888;
    display: flex;
    @media (max-width: 600px) {
    flex-direction: column;
    }
`

//경고문구
const _Subinputtitle = styled.span`
    font-size: 16px;
    margin-left: 10px;
    margin-top: 3px;
    color: red;
    @media (max-width: 600px) {
    font-size: 15px;
    margin-left: 0;
    }
`

//이용목적 및 이용내역 인풋
const _Input2 = styled.textarea`
    width: 300px;
    height: 200px;
    margin-left: 20px;
    border: 1px solid #888888;
    border-radius: 3px;
    padding-left: 5px;
    white-space: pre-line;
    @media (max-width: 600px) {
    margin-left: 0;
    font-size: 13px;
    }
`

//제출버튼부모
const Btnwrap = styled.div`
    display: flex;
    justify-content: center;
    button:last-child {
        border: 1px solid #888888;
    }
`

//제출버튼
interface Container{
    bg: any;
    color: any;
}
const _SubmitBtn = styled.button<Container>`
    width: 25vw;
    height: 60px;
    background: ${props => props.bg};
    color : ${props => props.color};
    border-radius: 15px;
    border: none;
    margin: 5px;
    margin-top: 15px;
    font-size: 16px;
    cursor: pointer;
    @media (max-width: 600px) {
    font-size: 13px;
    }
`

//장비대여메뉴
const _Menulist = styled.div`
    display: flex;
    flex-direction: column;
    grid-area: sbmenu;
`

//모달창 스타일
const ModalWrapper = styled.div`
    position: fixed;
    top: 0;
    left: 0;

    width: 100%;
    height: 100%;

    background-color: rgba(0, 0, 0, 0.4);
`;

const ModalContent = styled.div` 
    position: absolute;
    top: 50%;
    left: 50%;

    width: 55vw;
    height: 90vh;

    background-color: rgb(255, 255, 255);
    border-radius: 10px;
    box-shadow: 0 2px 3px 0 rgba(34, 36, 38, 0.15);

    transform: translateX(-50%) translateY(-50%);
`;

//모달창 내용
const _Headmodal = styled.h3`
    margin: 25px;
    padding-bottom: 10px;
    box-shadow: inset 0 -1px 0 #888888;
`

////////////////////////////////////////////////////////////////////작업중/////////////////////
const Cartwrap = styled.div`
    display: grid;
    grid-template-columns: repeat(5, 1fr);
    padding: 0 25px 0 25px;
    grid-column-gap: 10px;
`

const Cartproductwrap = styled.div`
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
`

const CartBorder = styled.div`
    height: 100%;
    width: 70%;
    display: flex;
    justify-content: center;
    align-items: center;
    margin-top: 5px;
`

const CartImg = styled.img`
    width: 60%;
`

const CartName = styled.div`
    font-size: 16px;
    font-weight: 500;
    text-align: center;
    margin-top: 5px;
`


const RentalListWrap = styled.div`
    padding: 0 25px 0 25px;
    /* margin-top: 150px; */
    display: grid;
    grid-template-columns: repeat(4, 1fr);
    grid-column-gap: 10px;
    grid-template-areas: 
        "sort sort sort sort"
        "product product product product"
        "product product product product"
        "btn btn btn btn"
`

const Sortmenu = styled.div`
    grid-area: sort;
    display: flex;
    box-shadow: inset 0 2px 0 #1E00D3;
    margin-top: 10px;
`

const _Sort = styled.div`
    font-size: 16px;
    padding: 15px;
    cursor: pointer;
    :hover{
        box-shadow: inset 0 -1px 0 #1E00D3;
    }
`

const Search = styled.input`
    grid-area: search;
    margin: 15px 5px 0 auto;
    border: 1px solid gray;
    border-radius: 10px;
    width: 130px;
    height: 20px;
`

const Productwrap = styled.div`
    display: flex;
    flex-direction: column;
    cursor: pointer;
`

const ImgBorder = styled.div`
    height: 100%;
    display: flex;
    justify-content: center;
    align-items: center;
    margin-top: 10px;
    grid-area: product;
`

const ProductImg = styled.img`
    width: 60%;
    grid-area: product;
`

const ProductName = styled.div`
    font-size: 16px;
    font-weight: 500;
    text-align: center;
    margin-top: 5px;
    grid-area: product;
`

const OKbtn = styled.button`
    position: fixed;
    bottom : 15px;
    right: 2vw;
    margin: 0 0 0 0;
    color: #ffffff;
    font-weight: 700;
    font-size: 15px;
    width: 6.5vw;
    height: 35px;
    border-radius: 8px;
    border: none;
    background-color: #1E00D3;
    grid-area: btn;
`

const Closebtn = styled.button`
    position: fixed;
    bottom : 15px;
    right: 9vw;
    color: #ffffff;
    font-weight: 700;
    font-size: 15px;
    width: 6.5vw;
    height: 35px;
    border-radius: 8px;
    border: none;
    background-color: #999999;
    grid-area: btn;
`

const Rentaldate = styled.div`
    >div{
        @media (max-width: 600px) {
            display: flex;
            flex-direction: column;
            >div{
                margin-left: 0;
                margin-top: 5px;
            }
        }
    }
`