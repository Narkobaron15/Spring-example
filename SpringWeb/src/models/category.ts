export default interface ICategoryItem {
    get id(): number;
    get name(): string;
    get imageURL(): string;
    get description(): string;
  }