import React, { useState } from 'react';

function Test() {
const [phoneNumber, setPhoneNumber] = useState('');

function handleChange(event:any) {
    const inputValue = event.target.value;
    // Remove all non-numeric characters from the input value
    const formattedValue = inputValue.replace(/[^0-9]/g, '');
    setPhoneNumber(formattedValue);
}

return (
    <label>
    Phone Number:
    <input 
        type="tel" 
        value={phoneNumber} 
        onChange={handleChange} 
        pattern="[0-9]{3}-[0-9]{3}-[0-9]{4}" 
        placeholder="123-456-7890" 
        required 
    />
    </label>
);
}

export default Test;
