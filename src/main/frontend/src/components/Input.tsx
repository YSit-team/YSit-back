import styled from 'styled-components';
import { ChangeEvent, useState } from 'react';


interface Props extends InputStyleProps {
  type?: 'password' | 'text';
  placeholder?: string;
  name?: string;
  value: string;
  onChange: (e: ChangeEvent<HTMLInputElement>) => void;
}

interface InputStyleProps {
  width?: number;
  height?: number;
  disabled?: boolean;
}

const Input = ({
  width = 380,
  height = 56,
  placeholder,
  name,
  value,
  onChange,
  disabled,
  type = 'text',
}: Props) => {

  return (
    <_Wrapper width={width} height={height}>
      <input
        value={value}
        name={name}
        onChange={onChange}
        placeholder={placeholder}
        disabled={disabled}
        type={type}
      />

      {/* 비밀번호 온클릭 표시 */}
      {/* {type === 'password' && (
        // <img
        //   src={isInVisible ? visibleIcon : invisibleIcon}
        //   alt="눈"
        //   onClick={() => setIsInVisible((prev) => !prev)}
        // />
        
      )} */}
    </_Wrapper>
  );
};

export default Input;

const _Wrapper = styled.label<InputStyleProps>`
  width: ${({ width }) => width}px;
  height: ${({ height }) => height}px;
  display: flex;
  align-items: center;

  > input {
    width: calc(${({ width }) => width || 380 - 60}px);
    height: 100%;
    color: gray;
    border: 1px solid gray;
    border-radius: 12px;
    padding: 0 20px;


    ::placeholder {
      color: gray;
      font-size: 14px;
    }
  }
  > img {
    margin-left: auto;
  }
`;
