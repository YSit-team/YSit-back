import styled from 'styled-components';

const TeamName = () => {
  return (
    <_Wrap>
    <_TeamName color="#000000">I Can Do</_TeamName>
    <_TeamName color='#1E00D3'> IT콘텐츠과</_TeamName>
    </_Wrap>
    )
};

export default TeamName;

const _Wrap = styled.h1`
    text-align: center;
`

const _TeamName = styled.span`
  display: inline;
  font-size: 36px;
  font-weight: bold;
  color: ${(props) => props.color};
  font-family: 'Inter', sans-serif;
`;