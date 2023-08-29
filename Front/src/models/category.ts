import { object, InferType, string } from 'yup';

export interface CategoryItem {
  get id(): number;
  get name(): string;
  get imageURL(): string;
  get description(): string;
}

export const catCreateSchema = object({
  name: string()
    .required('Name is required')
    .min(5, 'Name should be at least 5 characters long'),
  imageURL: string()
    .required('Image URL is required'),
  description: string()
    .required('Description is required')
    .min(10, 'Description should be at least 10 characters long'),
});

export type CategoryCreateItem = InferType<typeof catCreateSchema>;

export const catCreateInitVals: CategoryCreateItem = {
  name: '',
  imageURL: '',
  description: '',
};

export const catInitVals: CategoryItem = {
  id: 0,
  name: '',
  imageURL: '',
  description: '',
};