import { FormikErrors } from 'formik';
import CategoryImageDTO from '../../models/category_image';
import { IProductCreateModel } from '../../models/product/product';

type FilesArgs = {
    files?: File[] | null | undefined,
    setFilesCallback?: (value: File[]) => Promise<void | FormikErrors<IProductCreateModel>>,
    current_files?: CategoryImageDTO[],
    removeCurrentCallback?: (picture: CategoryImageDTO) => void,
}
type FileArgs = {
    src: string,
    name: string,
    delfunc: () => void,
}

export default function FilesComponent({ files, setFilesCallback, current_files, removeCurrentCallback }: FilesArgs) {
    return (
        <div className="form-group justify-evenly flex-wrap">
            {current_files?.map((file, i) => (
                <FileComponent key={i}
                    src={file.xs}
                    name={`Already attached image(${i + 1})`}
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
            <img src={src} alt='' />
            <span className="ml-5 self-center">{name}</span>
            <button type='button' className="p-0 bg-transparent" onClick={delfunc}>
                <i className="fa-solid fa-xmark"></i>
            </button>
        </div>
    )
}