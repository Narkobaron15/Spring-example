import { Link } from "react-router-dom";
import { ProductReadModel } from "../../../models/product/product";
import api_common from "../../../requests";
import StringUtils from "../../utils/stringutils";
import { faPenToSquare, faTrash } from "@fortawesome/free-solid-svg-icons";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { Dialog, DialogTitle, DialogContent, DialogContentText, DialogActions } from "@mui/material";
import { Button } from "flowbite-react";
import React from "react";
import { Transition } from "../../common/transition";

type ProductArgs = {
    params: ProductReadModel,
    removeCallback: () => void,
}

const MAX_TITLE_LENGTH = 25,
    MAX_DESCRIPTION_LENGTH = 35;


export default function ProductRowComponent({ params, removeCallback }: ProductArgs) {
    const [isModalOpen, setModalOpen] = React.useState(false);

    const handleOpen = () => setModalOpen(true);
    const handleClose = () => setModalOpen(false);

    const sendRequest = () => {
        api_common.delete(`/products/${params.id}`)
            .then(removeCallback);
    };

    return (
        <>
            <tr>
                <th scope="row">{params.id}</th>
                <td>
                    <img className="rounded-pic mx-4" src={params.primary_image?.sm} alt={params.name} />
                </td>
                <td>
                    <Link to={`/products/details/${params.id}`} className="underline" >
                        {StringUtils.ShortenString(params.name, MAX_TITLE_LENGTH)}
                    </Link>
                </td>
                <td>{StringUtils.ShortenString(params.description, MAX_DESCRIPTION_LENGTH)}</td>
                <td>{StringUtils.PriceToString(params.price)}</td>
                <td>{params.categoryName}</td>
                <td>
                    {/* Edit button */}
                    <Link to={`/products/update/${params.id}`} className="relative inline-flex items-center justify-center p-0.5 mb-2 mr-2 overflow-hidden text-sm font-medium text-gray-900 rounded-lg group bg-gradient-to-br from-teal-300 to-lime-300 group-hover:from-teal-300 group-hover:to-lime-300 dark:text-white dark:hover:text-gray-900 focus:ring-4 focus:outline-none focus:ring-lime-200 dark:focus:ring-lime-800">
                        <span className="relative px-2 py-1 transition-all ease-in duration-75 bg-white dark:bg-gray-900 rounded-md group-hover:bg-opacity-0">
                            <FontAwesomeIcon className="ml-px" icon={faPenToSquare} />
                        </span>
                    </Link>

                    {/* Delete button */}
                    <button onClick={handleOpen}
                        className="relative inline-flex items-center justify-center p-0.5 mb-2 mr-2 overflow-hidden text-sm font-medium text-gray-900 rounded-lg group bg-gradient-to-br from-pink-500 to-orange-400 group-hover:from-pink-500 group-hover:to-orange-400 hover:text-white dark:text-white focus:ring-4 focus:outline-none focus:ring-pink-200 dark:focus:ring-pink-800">
                        <span className="relative px-2 py-1 transition-all ease-in duration-75 bg-white dark:bg-gray-900 rounded-md group-hover:bg-opacity-0">
                            <FontAwesomeIcon icon={faTrash} />
                        </span>
                    </button>
                </td>
            </tr>
            <Dialog
                open={isModalOpen}
                TransitionComponent={Transition}
                onClose={handleClose}
                aria-describedby="alert-dialog-slide-description">
                <DialogTitle>Delete {params.name}?</DialogTitle>
                <DialogContent>
                    <DialogContentText>Are you sure that the category '{params.name}' should be deleted?</DialogContentText>
                </DialogContent>
                <DialogActions>
                    <Button onClick={handleClose}>Cancel</Button>
                    <Button onClick={sendRequest}>OK</Button>
                </DialogActions>
            </Dialog>
        </>
    );
}