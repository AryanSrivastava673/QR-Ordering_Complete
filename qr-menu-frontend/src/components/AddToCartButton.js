import React from 'react';
import axios from 'axios';
import { useNotification } from '../context/NotificationContext'

const AddToCartButton = ({ item, tableNo }) => {
    const { notify } = useNotification();
    const addToCart = async () => {
        try {
            const response = await axios.post(`http://localhost:8084/api/${tableNo}/addToCart`, item);
            console.log('Item added to cart:', response.data);
            notify({ message: 'Added to cart', type: 'success' });
        } catch (error) {
            console.error('Error adding item to cart:', error);
            alert('Failed to add item to cart.');
        }
    };

    return (
        <button onClick={addToCart}>Add to Cart</button>
    );
};

export default AddToCartButton;