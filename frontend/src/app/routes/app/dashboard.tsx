import { Button } from '@/components/catalyst/button';
import { ContentLayout } from '@/components/layouts';
import { Spinner } from '@/components/ui/spinner';
import { usePlants } from '@/features/plants/api/get-plants';
import { AddPlantBulkModal } from '@/features/plants/components/add-plant-bulk';
import { AddPlantModal } from '@/features/plants/components/add-plant-modal';
import { PlantCard } from '@/features/plants/components/plant-card';

const DashboardRoute = () => {
  const { isLoading, data } = usePlants({});

  if (isLoading) {
    return <Spinner />;
  }

  return (
    <ContentLayout
      title="Plants"
      action={
        <>
          <div className="ml-auto mr-4">
            <AddPlantBulkModal>Add Plant Bulk</AddPlantBulkModal>
          </div>
          <AddPlantModal>Add Plant</AddPlantModal>
        </>
      }
    >
      <div className="space-y-4 max-w-xl mx-auto">
        {data?.map((plant) => <PlantCard key={plant.id} plant={plant} />)}
      </div>
    </ContentLayout>
  );
};

export default DashboardRoute;
