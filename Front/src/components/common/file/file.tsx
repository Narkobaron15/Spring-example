import { faXmark } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import ProductImageDTO from '../../../models/product/product_image';

import './file.css';

type FilesArgs = {
    files?: File[] | null | undefined,
    setFilesCallback?: (value: File[]) => void,
    current_files?: ProductImageDTO[],
    removeCurrentCallback?: (picture: any) => void,
}
type FileArgs = {
    src: string,
    name: string,
    delfunc: () => void,
}

export default function FilesComponent({ files, setFilesCallback, current_files, removeCurrentCallback }: FilesArgs) {
    return (
        <div className="file-container form-group">
            {current_files?.map((file, i) => (
                <FileComponent key={i}
                    src={file.sm}
                    name={`Image ${file.id}`}
                    delfunc={() => removeCurrentCallback?.call({}, file)} />
            ))}
            {[...files ?? []].map(file => (
                <FileComponent key={file.name}
                    src={URL.createObjectURL(file)}
                    name={file.name}
                    delfunc={() => setFilesCallback?.call({}, files?.filter(f => f !== file) ?? [])} />
            ))}
        </div>
    )
}

export function FileComponent({ src, name, delfunc }: FileArgs) {
    return (
        <div className='file'>
            <img src={src} alt='Image thumbnail' />
            <span>{name}</span>
            <button type='button' className="p-0 bg-transparent" onClick={delfunc}>
                <FontAwesomeIcon icon={faXmark} />
            </button>
        </div>
    )
}