import React from 'react';
import { Link } from "react-router-dom";

import { toast } from "react-toastify";
import { Button } from "flowbite-react";
import { FontAwesomeIcon } from "@fortawesome/react-fontawesome";
import { faPenToSquare, faTrash } from "@fortawesome/free-solid-svg-icons";
import { Dialog, DialogActions, DialogContent, DialogContentText, DialogTitle, Slide } from "@mui/material";
import { TransitionProps } from "@mui/material/transitions";

import { CategoryItem } from "../../../models/category/category";
import api_common from "../../../requests";

const Transition = React.forwardRef(function Transition(
    props: TransitionProps & {
        children: React.ReactElement<any, any>;
    },
    ref: React.Ref<unknown>,
) {
    return <Slide direction="up" ref={ref} {...props} />;
});

type CategoryArgs = {
    instance: CategoryItem,
    removeCallback: (() => void) | null,
};

export default function CategoryComponent({ instance, removeCallback }: CategoryArgs) {
    const [isModalOpen, setModalOpen] = React.useState(false);

    const handleOpen = () => setModalOpen(true);
    const handleClose = () => setModalOpen(false);
    const sendRequest = () => {
        api_common.delete('/categories/' + instance.id)
            .then(() => {
                handleClose();
                removeCallback?.call({});
                toast.success("Deleted successfully");
            })
            .catch(err => toast.error(`Something wrong happened (${err.message})`))
    };

    return (
        <>
            <tr className="border-t category-row">
                <td><img src={instance.image.sm} alt={instance.name} /></td>
                <th scope='row'>{instance.id}</th>
                <td>{instance.name}</td>
                <td>{instance.description}</td>
                <td>
                    <Link to={`/categories/update/${instance.id}`} className="relative inline-flex items-center justify-center p-0.5 mb-2 mr-2 overflow-hidden text-sm font-medium text-gray-900 rounded-lg group bg-gradient-to-br from-teal-300 to-lime-300 group-hover:from-teal-300 group-hover:to-lime-300 dark:text-white dark:hover:text-gray-900 focus:ring-4 focus:outline-none focus:ring-lime-200 dark:focus:ring-lime-800">
                        <span className="relative px-2 py-1 transition-all ease-in duration-75 bg-white dark:bg-gray-900 rounded-md group-hover:bg-opacity-0">
                            <FontAwesomeIcon className="ml-px" icon={faPenToSquare} />
                        </span>
                    </Link>
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
                <DialogTitle>Delete {instance.name}?</DialogTitle>
                <DialogContent>
                    <DialogContentText>Are you sure that the category '{instance.name}' should be deleted?</DialogContentText>
                </DialogContent>
                <DialogActions>
                    <Button onClick={handleClose}>Cancel</Button>
                    <Button onClick={sendRequest}>OK</Button>
                </DialogActions>
            </Dialog>
        </>
    );
}