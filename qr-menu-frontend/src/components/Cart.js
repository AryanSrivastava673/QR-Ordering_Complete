import React, { useState, useEffect } from 'react';
import axios from 'axios';
import { useTableContext } from '../context/TableContext';
import './Cart.css';
import { useNotification } from '../context/NotificationContext'

const Cart = ({ onClose }) => {
    const [cartItems, setCartItems] = useState([]);
    const { tableNo } = useTableContext();
    const { notify } = useNotification();

    useEffect(() => {
        fetchCartItems();
    }, [tableNo]);

    const fetchCartItems = () => {
        axios.get(`http://localhost:8084/api/${tableNo}/getCart`)
            .then(response => setCartItems(response.data))
            .catch(error => console.error('Error fetching cart items:', error));
    };

    const handleRemoveFromCart = (menuItemId) => {
        axios.post(`http://localhost:8084/api/${tableNo}/removeFromCart/${menuItemId}`)
            .then(() => fetchCartItems())
            .catch(error => console.error('Error removing item from cart:', error));
    };

    const handlePlaceOrder = () => {
        axios.post(`http://localhost:8084/api/order/${tableNo}/placeOrder`)
            .then(response => {
                notify({message:'Order Placed!',type:'success'})
                onClose();
                fetchCartItems();
            })
            .catch(error => {
                console.error('Error placing order:', error);
                alert('Failed to place order.');
            });
    };

    const cartContainerStyle = {
        maxHeight: cartItems.length > 2 ? '200px' : `${100 * cartItems.length}px`
    };

    return (
        <div className="cart-container" style={cartContainerStyle}>
            <button onClick={onClose} className="close-button">X</button>
            {cartItems.length === 0 ? (
                <div className="empty-cart-message">Cart is empty</div>
            ) : (
                <div className="cart-content">
                    <ul>
                        {cartItems.map(item => (
                            <li key={item.id} className="cart-item">
                                <div className="item-details">
                                    <h4>{item.menuItemName}</h4>
                                    <p>Quantity: {item.quantity}</p>
                                    <p>Price: ${item.price.toFixed(2)}</p>
                                </div>
                                <button onClick={() => handleRemoveFromCart(item.menuItemId)} className="remove-btn">Remove</button>
                            </li>
                        ))}
                    </ul>
                    {cartItems.length > 0 && (
                         <div className="place-order-button-container">
                        <button onClick={handlePlaceOrder} className="place-order-btn">Place Order</button>
                         </div>
                    )}
                </div>
            )}
        </div>
    );
};

export default Cart;