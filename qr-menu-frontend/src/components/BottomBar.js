import React, { useState } from 'react';
import Cart from './Cart';
import './BottomBar.css'

const BottomBar = () => {
    const [isCartVisible, setCartVisible] = useState(false);

    const toggleCart = () => {
        setCartVisible(!isCartVisible);
    }

    return (
        <div style={{ position: 'fixed', bottom: 0, width: '100%', backgroundColor: '#ccc', padding: '10px', textAlign: 'center' }}>
            <button onClick={toggleCart}>View Cart</button>
            {isCartVisible && <Cart onClose={toggleCart}/>}
        </div>
    );
};

export default BottomBar;