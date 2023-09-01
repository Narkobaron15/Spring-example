export interface CategoryItem {
  get id(): number;
  get name(): string;
  get imageURL(): string;
  get description(): string;
}

export const catInitVals: CategoryItem = {
  id: 0,
  name: '',
  imageURL: '',
  description: '',
};