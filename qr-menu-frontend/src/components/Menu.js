// src/components/Menu.js

import React, { useState, useEffect } from 'react';
import axios from 'axios';
import AddToCartButton from './AddToCartButton'; // Import the "Add to Cart" Button component
import BottomBar from './BottomBar'; // Import BottomBar

import { useTableContext } from '../context/TableContext';

const Menu = () => {
    const [menuItemsByCategory, setMenuItemsByCategory] = useState({});
    const [expandedItemId, setExpandedItemId] = useState(null);
      const { tableNo } = useTableContext();

    useEffect(() => {
        axios.get(`http://localhost:8084/api/${tableNo}/menu/available`)
            .then(response => {
                const groupedItems = groupItemsByCategory(response.data);
                setMenuItemsByCategory(groupedItems);
            })
            .catch(error => console.error('Error fetching menu items:', error));
    }, []);

    const groupItemsByCategory = (items) => {
        return items.reduce((categories, item) => {
            const category = item.categoryName;
            if (!categories[category]) {
                categories[category] = [];
            }
            categories[category].push(item);
            return categories;
        }, {});
    }

    const toggleItemExpansion = (itemId, event) => {
        setExpandedItemId(expandedItemId === itemId ? null : itemId);
        event.stopPropagation(); // Prevent the click from propagating to the document
    }

    const getFirstSentence = (text) => {
        const result = text.match(/[^\.!\?]+[\.!\?]+/g);
        return result ? result[0] : text;
    }

    return (
        <div>
            {Object.keys(menuItemsByCategory).map(category => (
                <div key={category} id={category} className="category-container">
                    <h2>{category}</h2>
                    {menuItemsByCategory[category].map(item => (
                        <div
                            key={item.id}
                            className={`menu-item ${expandedItemId === item.id ? 'expanded' : ''}`}
                            // onClick={(event) => toggleItemExpansion(item.id, event)}
                        >
                            <h3>{item.name}</h3>
                            <p className="menu-item-description">
                                {expandedItemId === item.id ? item.description : getFirstSentence(item.description)}
                            </p>
                            <p>${item.price}</p>
                            <AddToCartButton item={item} tableNo={tableNo}/>
                        </div>
                    ))}
                </div>
            ))}
            <BottomBar />
        </div>
    );
};

export default Menu;