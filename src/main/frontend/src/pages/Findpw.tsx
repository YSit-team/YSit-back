import React, {useState} from 'react';

// const Findpw = () => {
//     return (
//       <div>
//         <h1>비밀번호 찾기</h1>
//         <p>비밀번호 찾기 페이지입니다.</p>
//       </div>
//     );
//   };
  
//   export default Findpw;
// import React, { useState } from 'react';

interface CartItem {
  name: string;
}

const ShoppingCart: React.FC = () => {
  const [cart, setCart] = useState<CartItem[]>([]);

  const addToCart = (item: CartItem) => {
    setCart([...cart, item]);
  };

  const removeFromCart = (index: number) => {
    setCart([...cart.slice(0, index), ...cart.slice(index + 1)]);
  };

  return (
    <div>
      <h2>Shopping Cart</h2>
      <ul>
        {cart.map((item, index) => (
          <li key={index}>
            {item.name}
            <button onClick={() => removeFromCart(index)}>Remove</button>
          </li>
        ))}
      </ul>
      <button onClick={() => addToCart({ name: 'Hi' })}>Add Item</button>
      <ObjectName></ObjectName>
    </div>
    
  );
};

export default ShoppingCart;



const ObjectName= () => {
  const [clickedName, setClickedName] = useState([]);

  const handleClick = (event: any) => {
    setClickedName(event.currentTarget.id);
  };

  return (
    <div>
      {/* <div id={id} onClick={handleClick}>{name}</div> */}
      <button id="object1" onClick={handleClick}>Object 1</button>
      <button id="object2" onClick={handleClick}>Object 2</button>
      <button id="object3" onClick={handleClick}>Object 3</button>
      <p>{clickedName ? `You clicked on ${clickedName}` : 'Click on an object to display its name'}</p>
    </div>
  );
};
