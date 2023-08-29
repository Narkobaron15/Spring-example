import { Link } from "react-router-dom";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faPenToSquare, faTrash } from "@fortawesome/free-solid-svg-icons";
import { CategoryItem } from "../../../models/category";
import React from 'react';

type CategoryArgs = {
    category: CategoryItem,
};

export default function CategoryComponent({ category }: CategoryArgs) {
    const [isModalOpen, setModalOpen] = React.useState(false);

    return (
        <>
            <tr className="border-t">
                <td><img src={category.imageURL} alt={category.name} /></td>
                <th scope='row'>{category.id}</th>
                <td>{category.name}</td>
                <td>{category.description}</td>
                <td>
                    <Link to={`/update/${category.id}`} className="relative inline-flex items-center justify-center p-0.5 mb-2 mr-2 overflow-hidden text-sm font-medium text-gray-900 rounded-lg group bg-gradient-to-br from-teal-300 to-lime-300 group-hover:from-teal-300 group-hover:to-lime-300 dark:text-white dark:hover:text-gray-900 focus:ring-4 focus:outline-none focus:ring-lime-200 dark:focus:ring-lime-800">
                        <span className="relative px-2 py-1 transition-all ease-in duration-75 bg-white dark:bg-gray-900 rounded-md group-hover:bg-opacity-0">
                            <FontAwesomeIcon className="ml-px" icon={faPenToSquare} />
                        </span>
                    </Link>
                    <button  className="relative inline-flex items-center justify-center p-0.5 mb-2 mr-2 overflow-hidden text-sm font-medium text-gray-900 rounded-lg group bg-gradient-to-br from-pink-500 to-orange-400 group-hover:from-pink-500 group-hover:to-orange-400 hover:text-white dark:text-white focus:ring-4 focus:outline-none focus:ring-pink-200 dark:focus:ring-pink-800">
                        <span className="relative px-2 py-1 transition-all ease-in duration-75 bg-white dark:bg-gray-900 rounded-md group-hover:bg-opacity-0">
                            <FontAwesomeIcon icon={faTrash} />
                        </span>
                    </button>
                </td>
            </tr>
        </>
    );
}