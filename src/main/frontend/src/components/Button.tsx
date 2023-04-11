import styled from 'styled-components';

interface Props extends ButtonStyleProps {
  children: React.ReactNode;
  onClick: () => void;
}

interface ButtonStyleProps {
  width?: number;
  height?: number;
  backgroundColor: string;
}

const Button = ({
  children,
  onClick,
  width = 380,
  height = 56,
  backgroundColor,
}: Props) => {
  return (
    <_Wrapper
      backgroundColor={backgroundColor}
      width={width}
      height={height}
      onClick={onClick}
    >
      {children}
    </_Wrapper>
  );
};
export default Button;

const _Wrapper = styled.button<ButtonStyleProps>`
  width: ${({ width }) => width}px;
  height: ${({ height }) => height}px;
  background-color: ${({ backgroundColor }) => backgroundColor};
  border-radius: 12px;
  color: #ffffff;
  font-size: 20px;
  /* display: flex;
  align-items: center;
  justify-content: center; */
  margin: 0 auto;
  font-weight: bold;
  border: none;
  cursor: pointer;
`;
