import React, { useState } from 'react';
import Cart from './Cart';
import './BottomBar.css'

const BottomBar = () => {
    const [isCartVisible, setCartVisible] = useState(false);

    const toggleCart = () => {
        setCartVisible(!isCartVisible);
    }

    return (
        <div className="bottom-bar">
            <button onClick={toggleCart} className="bottom-bar-btn">View Cart</button>
            {isCartVisible && <Cart onClose={toggleCart}/>} 
        </div>
    );
};

export default BottomBar;